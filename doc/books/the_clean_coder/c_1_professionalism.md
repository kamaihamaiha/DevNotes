## 第一章 专业主义

- [1.1 清楚你要什么](#11-清楚你要什么)
- [1.2 担当重任](#12-担当重任)
- [1.3 首先，不行损害之事](#13-首先不行损害之事)
- [1.4 职业道德](#14-职业道德)

---

### 1.1 清楚你要什么

“专业主义”有很深的含义，它不但象征着荣誉与骄傲，而且明确意味着责任与义务。这两者密切相关，因为从你无法负责的事情上不可能获得荣誉与骄傲。
做个非专业人士可轻松多了。非专业人士不需要为自己所做的工作负责，他们大可把责任推给雇主。如果非专业人士把事情搞砸了，收拾摊子的往往是雇主；
而专业人士如果犯了错，只好自己收拾残局。如果你不小心放过了某个模块里的一个bug，以致公司损失了1万美元，结果将会怎样呢？非专业人士会耸耸肩说：
“难免要出点儿状况嘛。”然后像没事儿人一样继续写其他模块。而专业人士会自己为公司的那1万美元买单[1]！哇，自掏腰包？那可真让人心疼唉！
但专业人士就必须这么做。实际上，专业主义的精髓就在于将公司利益视同个人利益。看到了吧，“专业主义”就意味着担当责任。

### 1.2 担当重任

我（本书作者）曾因不负责任尝尽了苦头，所以明白尽职尽责的重要意义.

### 1.3 首先，不行损害之事

- [1.3.1 不要破坏软件功能](#131-不要破坏软件功能)
- [1.3.2 不要破坏结构](#132-不要破坏结构)

#### 131 不要破坏软件功能

要对自己的不完美负责。代码中难免会出现bug，但这并不意味着你不用对它们负责；没人能写出完美的软件，但这并不表示你不用对不完美负责。
所谓专业人士，就是能对自己犯下的错误负责的人，哪怕那些错误实际上在所难免。所以，雄心勃勃的专业人士们，你们要练习的第一件事就是“道歉”。
道歉是必要的，但还不够。你不能一而再、再而三地犯相同的错误。职业经验多了之后，你的失误率应该快速减少，甚至渐近于零。
失误率永远不可能等于零，但你有责任让它无限接近零。

- [1.3.1.1](#1311-让qa找不出任何问题)
- [1.3.1.2](#1312-要确信代码正常运行)
- [1.3.1.3](#1313-自动化qa)

##### 1.3.1.1 让QA找不出任何问题

QA会发现bug吗？可能会吧，所以，准备好道歉吧，然后反思那些bug是怎么逃过你的注意的，想办法防止它再次出现。每次QA找出问题时，更糟糕的是用户找出问题时，你都该震惊羞愧，并决心以此为戒。

##### 1.3.1.2 要确信代码正常运行

要实行自动化测试。写一些随时都能运行的单元测试，然后尽可能多地执行这些测试。
要用这些自动化单元测试去测多少代码呢？还要说吗？全部！全部都要测！我是在建议进行百分百测试覆盖吗？不，我不是在建议，我是在要求
！你写的每一行代码都要测试。完毕！这是不是不切实际？当然不是。你写代码是因为想执行它，如果你希望代码可以执行，那你就该知道它是否可行。
而要知道它是否可行，就一定要对它进行测试。

有个开源项目[FitNesse](https://github.com/unclebob/fitnesse) 单元测试有很多，可参考下。

##### 1.3.1.3 自动化QA

FitNesse的整个QA流程即是执行单元测试和验收测试。如果这些测试通过了，我就会发布软件。这意味着我的QA流程大概需要3分钟，只要我想要，可以随时执行完整的测试流程。
作为开发人员，你需要有个相对迅捷可靠的机制，以此判断所写的代码可否正常工作，并且不会干扰系统的其他部分。因此，你的自动化测试至少要能够让你知道，你的系统很有可能通过QA的测试。


#### 132 不要破坏结构

所有软件项目的根本指导原则是，软件要易于修改。如果违背这条原则搭建僵化的结构，就破坏了构筑整个行业的经济模型。简言之，你必须能让修改不必花太高代价就可以完成。
如果你希望自己的软件灵活可变，那就应该时常修改它！要想证明软件易于修改，唯一办法就是做些实际的修改。如果发现这些改动并不像你预想的那样简单，
你便应该改进设计，使后续修改变简单。该在什么时候做这些简单的小修改呢？随时！关注哪个模块，就对它做点简单的修改来改进结构。每次通读代码的时候，
也可以不时调整一下结构。这一策略有时也叫“无情重构”，我把它叫作“童子军训练守则”：对每个模块，每检入一次代码，就要让它比上次检出时变得更为简洁。
每次读代码，都别忘了进行点滴的改善。这完全与大多数人对软件的理解相反。他们认为对上线运行的软件不断地做修改是危险的。错！让软件保持固定不变才是危险的！
如果一直不重构代码，等到最后不得不重构时，你就会发现代码已经“僵化了”。

### 1.4 职业道德

你应该计划每周工作60小时。前40小时是给雇主的，后20小时是给自己的。在这剩余的20小时里，你应该看书、练习、学习，或者做其他能提升职业能力的事情。
一周有168小时，给你的雇主40小时，为自己的职业发展留20小时，剩下的108小时再留56小时给睡眠，那么还剩52小时可做其他的事呢。或许你不愿那么勤勉。没问题。
只是那样的话你也不能自视为专业人士了，因为所谓“术业有专攻”那也是需要投入时间去追求的。或许你会觉得工作就该在上班时完成，不该再带回家中。赞成！那20小时你不用为雇主工作。
相反，你该为自己的职业发展工作。有时这两者并不矛盾，而是一致的。有时你为雇主做的工作让你个人的职业发展受益匪浅，这种情况下，在那20小时里花点时间为雇主工作也是合理的。
但别忘了，那20小时是为你自己的。它们将会让你成为更有价值的专业人士。或许你会觉得这样做只会让人精力枯竭。恰恰相反，这样做其实能让你免于枯竭匮乏。
假设你是因为热爱软件而成为软件开发者，渴望成为专业开发者的动力也正是来自对软件的热情，那么在那20小时里，就应该做能够激发、强化你的热情的事。那20小时应该充满乐趣！

- [1.4.1 了解你的领域](#141-了解你的领域)
- [1.4.2 坚持学习](#142-坚持学习)
- [1.4.3 练习](#143-练习)
- [1.4.4 合作](#144-合作)
- [1.4.5 辅导](#145-辅导)
- [1.4.6 了解业务领域](#146-了解业务领域)
- [1.4.7 与雇主/客户保持一致](#147-与雇主客户保持一致)
- [1.4.8 谦逊](#148-谦逊)


#### 1.4.1 了解你的领域

那些在过去50年中来之不易的理念，绝大部分在今天仍像过去一样富有价值，甚至宝贵了。别忘了桑塔亚纳的诅咒：“不能铭记过去的人，注定要重蹈覆辙。”

下面列出了每个专业软件开发人员必须精通的事项:

- 设计模式。必须能描述GOF书中的全部24种模式，同时还要有POSA书中的多数模式的实战经验。
- 设计原则。必须了解SOLID原则，而且要深刻理解组件设计原则。
- 方法。必须理解XP、Scrum、精益、看板、瀑布、结构化分析及结构化设计等。
- 实践。必须掌握测试驱动开发、面向对象设计、结构化编程、持续集成和结对编程。
- 工件。必须了解如何使用UML图、DFD图、结构图、Petri网络图、状态迁移图表、流程图和决策表。

#### 1.4.2 坚持学习

软件行业的飞速改变，意味着软件开发人员必须坚持广泛学习才不至于落伍。不写代码的架构师必然遭殃，他们很快会发现自己跟不上时代了；
不学习新语言的程序员同样会遭殃，他们只能眼睁睁看着软件业一路发展，把自己抛在后面；学不会新规矩和新技术的开发人员更可怜，
他们只能在日渐沦落的时候看着身边人越发优秀。你会找那些已经不看医学期刊的医生看病吗？你会聘请那些不了解最新税法和判例的税务律师吗？
雇主们干吗要聘用那些不能与时俱进的开发人员呢？读书，看相关文章，关注博客和微博，参加技术大会，访问用户群，多参与读书与学习小组。
不懂就学，不要畏难。如果你是.NET程序员，就去学学Java；如果你是Java程序员，就去学学Ruby；如果你是C语言程序员，就去学学Lisp；
如果你真想练练脑子，就去学学Prolog和Forth吧！


#### 1.4.3 练习

软件开发者该怎样来不断训练自己呢？本书会用一整章的篇幅来谈论各种练习技巧，所以在此先不赘述了。简单说，我常用的一个技巧是重复做一些简单的练习，
如“保龄球游戏”或“素数筛选”，我把这些练习叫作“卡塔”（kata）[3]。卡塔有很多类型。卡塔的形式往往是一个有待解决的简单编程问题，
比如编写计算拆分某个整数的素数因子等。练卡塔的目的不是找出解决方法（你已经知道方法了），而是训练你的手指和大脑。
每天我都会练一两个卡塔，时间往往安排在正式投入工作之前。我可能会选用Java、Ruby、Clojure或其他我希望保持纯熟的语言来练习。
我会用卡塔来培养某种专门的技能，比如让我的手指习惯点击快捷键或习惯使用某些重构技法等。不妨早晚都来个10分钟的卡塔吧，把它当作热身练习或者静心过程。

#### 1.4.4 合作

学习的第二个最佳方法是与他人合作。专业软件开发人员往往会更加努力地尝试与他人一起编程、一起练习、一起设计、一起计划，这样他们可以从彼此身上学到很多东西，而且能在更短的时间内更高质量地完成更多工作。

#### 1.4.5 辅导

俗话说：教学相长。想迅速牢固地掌握某些事实和观念，最好的办法就是与你负责指导的人交流这些内容。这样，传道授业的同时，导师也会从中受益。
同样，让新人融入团队的最好办法是和他们坐到一起，向他们传授工作要诀。专业人士会视辅导新人为己任，他们不会放任未经辅导的新手恣意妄为。

#### 1.4.6 了解业务领域

每位专业软件开发人员都有义务了解自己开发的解决方案所对应的业务领域。如果编写财务系统，你就应该对财务领域有所了解；
如果编写旅游应用程序，那么你需要去了解旅游业。你未必需要成为该领域的专家，但你仍需要用功，付出相当的努力来认识业务领域。
开始一个新领域的项目时，应当读一两本该领域相关的书，要就该领域的基础架构与基本知识作客户和用户访谈，还应当花时间和业内专家交流，了解他们的原则与价值观念。
最糟糕、最不专业的做法是，简单按照规格说明来编写代码，但却对为什么那些业务需要那样的规格定义不求甚解。相反，你应该对这一领域有所了解，能辨别、质疑规格说明书中的错误。

#### 1.4.7 与雇主/客户保持一致

雇主的问题就是你的问题。你必须弄明白这些问题，并寻求最佳的解决方案。每次开发系统，都应该站在雇主的角度来思考，确保开发的功能真正能满足雇主的需要。
开发人员之间互相认同是容易的，但把一方换成雇主，人们就容易产生“彼”“此”之分。专业人士会尽全力避免这样的狭隘之见。

#### 1.4.8 谦逊

编程是一种创造性活动。写代码是无中生有的创造过程，我们大胆地从混沌之中创建秩序。我们自信地发布准确无误的指令，稍有差错，机器的错误行为就可能造成无法估量的损失。
因此，编程也是极其自负的行为。专业人士知道自己自负，不会故作谦逊。他们熟知自己的工作，并引以为荣；他们对自己的能力充满自信，并因此勇于承担有把握的风险。
专业人士不是胆小鬼。然而，专业人士也知道自己会摔跟头，自己的风险评估也有出错的时候，自己也有力不从心的时候。这时候，如果他们照照镜子，会看到那个自负的傻瓜正对着自己笑。
因此，在发现自己成为笑柄时，专业人士会第一个发笑。他从不会嘲讽别人，自作自受时他会接受别人的嘲讽。反之，他则会一笑了之。他不会因别人犯错就对之横加贬损，因为他知道，
自己有可能就是下一个犯错的人。专业人士都清楚自己的自负，也知道上天会注意到这种自负，并加以惩戒。如若果真遭遇挫折，最好的办法就是按照霍华德说的——一笑了之吧！
