export class Queue {
  
    constructor() {
      this.items = [];
    }
  
    // Add an element to the queue
    enqueue(item) {
      this.items.push(item);
    }
// Add array element to the queue
    enqueues(items) {

    this.items.push(...items);
    }
  
    // Remove and return the front element from the queue
    dequeue() {
      return this.items.shift();
    }
  
    // Get the front element of the queue without removing it
    front() {
      return this.items[0];
    }
  
    // Check if the queue is empty
    isEmpty() {
      return this.items.length === 0;
    }
  
    // Get the size of the queue
    size() {
      return this.items.length;
    }
  
    // Clear the queue
    clear() {
      this.items = [];
    }
  }
