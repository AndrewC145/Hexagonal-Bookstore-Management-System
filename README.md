## Video Reflection

[![YIP](https://img.youtube.com/vi/dRD06M4h-n8/0.jpg)](https://www.youtube.com/watch?v=dRD06M4h-n8)

## Reflection Questions

2. Concurrency Challenges:

Making a system thread-safe is hard because multiple threads might try to change the same data at the same time, causing race conditions or inconsistent results. We also have to make sure one thread’s changes are visible to others and avoid deadlocks where threads get stuck waiting for each other. To keep the system correct but still fast, we only use synchronization when needed. For example, synchronized blocks make code simple but can slow things down because only one thread can enter at a time. ReentrantReadWriteLock lets many threads read at once but still locks for writing, which is faster for read-heavy tasks. ConcurrentHashMap is even better for shared data like caches because it manages locking internally and is very efficient. The main goal is to protect data while keeping performance good  correctness first, then speed.




4. When would you choose a simple ArrayList over a more complex Trie for search operations? What factors influence this decision? How do you measure and validate performance improvements

A simple ArrayList would be better over a Trie for search operations when it comes to small dataset, or in other words, there are not that many words being stored which will make a linear search being a lot more easier to implement on an ArrayList to see if it contains the prefix using .contains(), or another factor is when you don’t need to do lookups that often. With that as well in small datasets, they have better memory performance because they are contiguous compared to Trie where each node will have the data and the child pointer.

Therefore, Trie’s are typically better when it comes to search operations especially in large datasets when it takes less steps to find the words given a prefix, but in smaller datasets where less words are used, an ArrayList is easier to implement. Another way is that if whole words are used instead of prefixes, Arraylist would be better since you can just use the .contains() method.



5. Quality Assurance: 

Unit tests check if our code works, but they don’t always prove that tests are strong. Mutation testing helps by making small changes to the code on purpose and checking if the tests fail  if they don’t, our tests are weak. Static analysis tools like SonarLint or PMD check our code automatically for bugs or bad design without running it. Together, these methods give more confidence that our code is both correct and high quality. Code coverage shows how much code is tested, but a high percentage doesn’t always mean good tests. Mutation score shows how many fake bugs mutations our tests caught  this is a better sign of real test strength. Both are important because coverage tells us what we tested, and mutation score tells us how well we tested it.




6. How would you modify this system to handle 1 million materials instead of 10,000? Which components would need the most attention? How would you implement horizontal scaling for the search and caching components?  

To handle 1 million materials, the repository should move from a local JSON-based implementation to a distributed database like PostgreSQL, MongoDB, or Elasticsearch, while the search component should use a distributed search engine with sharding. The repository and search layers require the most attention due to memory and performance constraints. Horizontal scaling can be achieved by running multiple stateless application instances behind a load balancer, using a distributed cache like Redis or Memcached to share cached data across instances.

7. How do the different patterns (Observer, Chain of Responsibility) handle error conditions? What are the trade-offs between fail-fast and fail-safe error handling strategies?  
   
The Chain of Responsibility handles errors sequentially, often using a fail-fast approach for invalid input, immediately throwing exceptions to prevent invalid data from propagating. The Observer pattern uses a fail-safe strategy, where a failure in one observer does not stop notifications to others, ensuring system resilience. Fail-fast improves reliability and early bug detection but can halt processes, while fail-safe prioritizes availability but may delay error discovery.

8. How does property-based testing differ from traditional unit testing? When is each approach most valuable? How do you design effective property-based tests for complex business logic?  

Property-based testing (PBT) validates general invariants over a wide range of randomized inputs, while traditional unit testing (TUT) checks specific scenarios. TUT is useful for verifying defined business rules and boundary cases, whereas PBT is valuable for testing complex logic and discovering edge cases. Effective PBT focuses on system invariants, such as ensuring filtering always returns valid results or that operations like adding and removing components are reversible.

9. Which aspects of this lab’s implementation make the code easier to maintain and extend? What could be improved? How do design patterns contribute to maintainability, and what are their potential drawbacks?  
   
Maintainability is supported by the Hexagonal Architecture, SOLID principles, and patterns like Decorator and Observer, which enable modular, loosely coupled components and easier feature addition. Quality gates and testing reinforce reliability. Areas for improvement include managing concurrency complexity (e.g., StampedLock) and the learning curve from multiple integrated design patterns, which can introduce abstraction overhead and make the system harder for new developers to grasp.


10. Professional Practice:

In Lab 3, quality gates made sure our code met certain standards before being accepted like no big bugs, good test coverage, and clean code. Benchmarking tested how fast parts of the system ran, such as caching or searching. These practices are used in real software companies to keep code reliable while still developing quickly. To balance speed and quality, we can work fast but always test and review code before merging. If I could add something, I’d include automatic style checks and simple performance tests in every build, so we keep improving quality without slowing down development.

11. Pattern Integration:
The patterns in Lab 3 all work together to keep the bookstore system flexible and easy to update. For example, the Factory creates different Material types, the Decorator adds new features like gift wrapping, and the Observer notifies other parts when prices change. These patterns complement each other  one focuses on creation, another on behavior, and another on communication. The challenge is making them work together without adding too much complexity. That’s where Hexagonal Architecture helps it separates the domain logic from outside parts like storage or UI. This keeps patterns organized in their own layers, so we can test, change, or add new patterns later without breaking existing code.


13. Why was a Trie chosen for prefix search over other data structures? What are the memory vs time complexity trade-offs? How would you optimize the Trie implementation for a production system?

A Trie was chosen because of its ability to store strings as a single character for each node, character by character, and the path from the root to a specific node represents a prefix. One of the reasons why a Trie is preferred over another data structure like an ArrayList for example, is the performance for searching an element. Searching in a Trie is O(P + R) where P = prefix length and R = number of results, compared to an ArrayList where checking every word that starts with a prefix is O(N * M) where N is the number of words and M is the length of the prefix. Although its time complexity is better than something like an ArrayList, an ArrayList has better memory complexity, O(N) where N is the number of elements stored, compared to a Trie where it is O(total characters) + stored object references. But generally, a Trie is preferred because you just have to follow the characters by comparing at one character per level instead of comparing to an entire string, which also leads to less comparison towards entire strings.

In a production system environment, one issue is having to create a new node for every single character which can use a lot of memory. One solution is to use something called a Radix Trie where instead of storing a single character on one of its edges, you instead store the whole word, which will use less nodes and in turn less memory, but is a lot harder to implement.

13. Explain the LRU cache implementation and its benefits. How would you handle cache invalidation in a distributed system? What are the trade-offs between different cache eviction policies?

    
A least recently used cache is implemented by using a hashmap and a doubly linked list, hashmap to store a fixed number of  values, and the linked list to keep track of the access/used order, with the tail or the right side being the most recently used, and the head/left side being the least recently used. A LRU cache is good for storing frequently used data near the CPU, typically in memory with predictability to keep track of what you frequently use, and will evict the least recently used items to make more room for new ones.

Every time an element is used in a LRU cache it will be moved to the end, marking it as the most recently used, and so adding a new element will make it the most recently used, naturally making whatever value at the far left the least recently used and evicts it when the cache reaches full capacity.

For cache invalidation, one way to handle it would be using a Time-To-Live (TTL) for automatic expiration where each cache element has a timer, and if that timer goes to 0, the cache would be considered expired and be evicted, essentially meaning that if an element is not used again within a certain amount of time it will be automatically removed.

The trade off with LRU is that it assumes the order of access accurately reflects the usefulness of an item, and it often requires additional memory to store extra data like timestamps or maintain access order. For a least frequently used (LFU), an LFU may not perform well when it is first being initialized since it relies on the other elements' access patterns, and a new element is vulnerable to being evicted.

Another example is a First-In-First-Out (FIFO) where it strictly follows the order of entry which may not represent the actual importance of the elements. Meaning there is no way to tell if a certain element is used a lot or less. Essentially, it is unable to adapt to determine what kind of data is truly important, and may accidentally evict important data due to the order it follows.
