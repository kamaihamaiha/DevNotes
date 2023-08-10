package android.print;

import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;

import java.io.File;

public class PdfPrint {
    private final PrintAttributes printAttributes;
    private boolean isPrinting;

    public PdfPrint(PrintAttributes printAttributes) {
        this.printAttributes = printAttributes;
    }

    public void print(final PrintDocumentAdapter printAdapter, final File path, final String fileName, final CallbackPrint callback) {
        if (isPrinting) return;
        printAdapter.onLayout(null, printAttributes, null, new PrintDocumentAdapter.LayoutResultCallback() {
            @Override
            public void onLayoutFinished(PrintDocumentInfo info, boolean changed) {
                isPrinting = true;
                printAdapter.onWrite(new PageRange[]{PageRange.ALL_PAGES}, getOutputFile(path, fileName), new CancellationSignal(),
                        new PrintDocumentAdapter.WriteResultCallback() {
                            @Override
                            public void onWriteFinished(PageRange[] pages) {
                                super.onWriteFinished(pages);

                                isPrinting = false;
                                if (pages.length > 0) {
                                    File file = new File(path, fileName);
                                    String path = file.getAbsolutePath();
                                    callback.success(path);
                                } else {
                                    callback.onFailure("onWriteFinished pages is zero!");
                                }

                            }

                            @Override
                            public void onWriteFailed(CharSequence error) {
                                super.onWriteFailed(error);
                                isPrinting = false;
                                callback.onFailure("onWriteFailed: " + error == null ? "" : error.toString());
                            }

                            @Override
                            public void onWriteCancelled() {
                                super.onWriteCancelled();
                                isPrinting = false;
                                callback.onFailure("onWriteCancelled");
                            }
                        });

            }
        }, null);
    }


    private ParcelFileDescriptor getOutputFile(File path, String fileName) {
        if (!path.exists()) {
            path.mkdirs();
        }
        File file = new File(path, fileName);
        try {
            file.createNewFile();
            return ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_WRITE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public interface CallbackPrint {
        void success(String path);

        void onFailure(String msg);
    }
}
