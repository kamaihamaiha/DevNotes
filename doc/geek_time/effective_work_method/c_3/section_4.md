## 大师级程序员的工作秘笈

- 大多数程序员之所以开发效率低，很多时候是没想清楚就动手了
- [微操作](#微操作)

### 微操作

- 任务分解的关键在于：小
- 小到什么程度呢？有时甚至可以小到你可能认为这件事不值得成为一件独立的事。比如升级一个依赖的版本，做一次变量改名。
- 这样做的好处是什么呢？它保证了我可以随时停下来。
- 我们写程序的时候，都不喜欢被打扰，因为一旦被打扰，接续上状态需要很长一段时间，毕竟，我们可不像操作系统那么容易进行上下文切换。
- 但如果任务足够小，完成一个任务，我们选择可以进入到下一个任务，也可以停下来。这样，即便被打扰，我们也可以很快收尾一个任务，不至于被影响太多。
- 其实，这种极其微小的原子操作在其他一些领域也有着自己的应用。有一种实践叫微习惯，以常见的健身为例，很多人难以坚持，主要是人们一想到健身，就会想到汗如雨下的健身场景，想想就放弃了。
  - 但如果你一次只做一个俯卧撑呢？对大多数人来说，这就不是很难的一件事，那就先做一个。做完了一个如果你还想做，就接着做，不想做就不做了。
        ```
          一个俯卧撑？你会说这也叫健身，一个俯卧撑确实是一个很小的动作，重要的是，一个俯卧撑是你可以坚持完成的，如果每天做 10 个，恐怕这都是大多数人做不到的。
          我们知道，养成一个习惯，最难的是坚持。如果你有了一个微习惯，坚持就不难了。我曾经在 github 上连续提交代码 1000 天，这是什么概念？差不多三年的时间里，
          每天我都能够坚持写代码，提交代码，这还不算工作上写的代码。对于大多数人来说，这是不可思议的。但我坚持做到了，不是因为我有多了不起，而是我养成了自己的微习惯。
          这个连续提交的基础，就是我自己在练习任务分解时，不断地尝试把一件事拆细，这样，我每天都至少能保证完成一小步。当然，如果有时间了，
          我也会多写一点。正是通过这样的方法，我坚持了 1000 天，也熟练掌握了任务分解的技巧。
          ```

#### 微操作与分支模型

经过这种练习之后，任务分解也就成了我的本能，不再局限于写程序上。我遇到任何需要解决的问题，脑子里的第一反应一定是，它可以怎么一步一步地完成，确定好分解之后，解决问题就是一步一步做了。如果不能很好地分解，那说明我还没想清楚，还需要更多信息，或者需要找到更好的解决方案。一旦你懂得了把任务分解的重要性，甚至通过训练能达到微操作的水准，你就很容易理解一些因为步子太大带来的问题。举一个在开发中常见的问题，代码开发的分支策略。关于分支策略，行业里有很多不同的做法。有的团队是大家都在一个分支上写代码，有的是每个人拉出一个分支，写完了代码再合并回去。你有没有想过为什么会出现这种差异呢？行业中的最佳实践是，基于主分支的模型。大家都在同一个分支上进行开发，毕竟拉分支是一个麻烦事，虽然 git 的出现极大地降低了拉分支的成本。但为什么还有人要拉出一个分支进行开发呢？多半的原因是他写的代码太多了，改动量太大，很难很快地合到开发的主分支上来。那下一个问题就来了，为什么他会写那么多代码，没错，答案就是步子太大了。如果你懂得任务分解，每一个分解出来的任务要改动的代码都不会太多，影响都在一个可控的范围内，代码都可以很快地合并到开发的主分支上，也就没有必要拉分支了。在我的实际工作中，我带的团队基本上都会采用基于主分支的策略。只有在做一些实验的时候，才会拉出一个开发分支来，但它并不是常态。