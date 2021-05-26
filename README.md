# Performance Experiments

## Matrix Multiplication

This is a very naïve matrix multiplication used to demonstrate how memory layout of data structure may affect performance. 


|Name                    |Elapsed time (ms)|
|------------------------|----------------:|
|NaiveMatrix             |           64389 |
|NaiveMultithreadedMatrix|            7652 |
|FastMatrix              |            2574 |
|FastMultithreadedMatrix |             380 |
|FastForkJoinPoolMatrix  |             472 |


## False sharing

> In symmetric multiprocessor (SMP) systems, each processor has a local cache. The memory system must guarantee cache coherence. False sharing occurs when threads on different processors modify variables that reside on the same cache line. This invalidates the cache line and forces an update, which hurts performance.

https://software.intel.com/content/www/us/en/develop/articles/avoiding-and-identifying-false-sharing-among-threads.html

<img src="assets/False%20sharing%20effect%20on%20performance.svg"/>

|step|threads |ops                |
|----|-------:|:------------------|
|1   |1       |0.8448008856416039 |
|1   |2       |0.6002473452330577 |
|1   |4       |0.509123680035285  |
|1   |8       |0.29828577674863793|
|1   |16      |0.1569152581110366 |
|1   |1       |0.8330303454969695 |
|2   |2       |0.6030883767112257 |
|2   |4       |0.5083747296010495 |
|2   |8       |0.3354684132439413 |
|2   |16      |0.200205158746362  |
|3   |1       |0.8331952901129724 |
|3   |2       |0.6084429017144153 |
|3   |4       |0.5180124395774953 |
|3   |8       |0.4272909327186569 |
|3   |16      |0.277217802508264  |
|4   |1       |0.8402723909595498 |
|4   |2       |0.8315961062590683 |
|4   |4       |0.5905676616436388 |
|4   |8       |0.46096593655643614|
|4   |16      |0.30447849753753065|
|5   |1       |0.8303849856193068 |
|5   |2       |0.818488219345203  |
|5   |4       |0.603732861621239  |
|5   |8       |0.49738334987827737|
|5   |16      |0.32361556784043943|
|6   |1       |0.8388954115947542 |
|6   |2       |0.8283862657598025 |
|6   |4       |0.7438973091508108 |
|6   |8       |0.5526942843559683 |
|6   |16      |0.3530043829267051 |
|7   |1       |0.8264981466119867 |
|7   |2       |0.83810695913983   |
|7   |4       |0.7508310256968036 |
|7   |8       |0.5895185253674669 |
|7   |16      |0.3825844958984687 |
|8   |1       |0.8426661520208776 |
|8   |2       |0.8224229638760363 |
|8   |4       |0.7523539304851876 |
|8   |8       |0.630657609463567  |
|8   |16      |0.4543038701672635 |