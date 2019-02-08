//Taylor Kinsey
//010766436


import java.util.concurrent.Semaphore; // import semaphore utility
import java.util.Random; //import random utility

public class ProducerConsumer{


  public static void main(String args[]){
		
    
    BoundedBuffer buff = new BoundedBuffer();
          int sleep = 0;
          int consumerthread = 0;
          int producerthread = 0;

          

          try{
          sleep = Integer.parseInt(args[0]);
          System.out.println(sleep);
          }

          catch(NumberFormatException nfe){
          System.out.println("The First argument needs to be an integer");
          System.exit(1);
          }

          try{
          consumerthread = Integer.parseInt(args[1]);
          //System.out.println(consumerthread);
          }

          catch(NumberFormatException nfe){
          System.out.println("The First argument needs to be an integer");
          System.exit(1);
          }

          try{
          producerthread = Integer.parseInt(args[2]);
          //System.out.println(producerthread);
          }

          catch(NumberFormatException nfe){
          System.out.println("The First argument needs to be an integer");
          System.exit(1);
          }
 
    //create producers
    for(int i = 0; i < producerthread; i++)
		{
      Thread producer = new Thread(new Producer(buff,sleep));
      producer.start();
    }

    //create consumers
    for(int i = 0; i < consumerthread; i++)
		{
      Thread consumer = new Thread(new Consumer(buff,sleep));
      consumer.start();
    }

    //wait for time seconds
    long  = System.currentTimeMillis() + sleep * 1000;
		
    long now = System.currentTimeMillis();
		
    while(now <= stop)
		{
      now = System.currentTimeMillis();
    }

    //end program
    System.exit(0);

       System.out.println("Using arguments from command line");
    System.out.println("Sleep time = " + sleep);
    System.out.println("Producer threads = " + producerthread);
    System.out.println("Consumer threads = " + consumerthread);

		
  }
}



class BoundedBuffer 
{
  public static final int size = 5;
  private Object[] buffer; 
  private Semaphore mutex; 
  private Semaphore empty; 
  private Semaphore full; 
	
  public int count;
  private int in; 
  private int out;

  
  public BoundedBuffer()
	{
    buffer = new Object[size];
    mutex = new Semaphore(1);
    empty = new Semaphore(size);
    full = new Semaphore(0);
    count = 0;
    in = 0;
    out = 0;
  }

  
  public boolean insert(Object item)
	{
    
    try
		{
      
      empty.acquire();
      mutex.acquire();

      count++;
      buffer[in] = item;
      in = (out + 1) % (size);
      System.out.println("Producer produced " + item + "");

      mutex.release();
      full.release();
     
      return true;
    }
		
    catch(InterruptedException e)
		{
      System.out.println("Insert ERROR : " + e);
    }
    return false;
  }

 
  public boolean remove()
	{
  
    try
		{
      Object item = null;

      full.acquire();
      mutex.acquire();

      count--;
      item = buffer[out];
      out = (out + 1) % (size);
      System.out.println("Consumer consumed" + item + "");

      mutex.release();
      empty.release();
      return true;
    }
		
    catch(InterruptedException e)
		{
      System.out.println("Remove ERROR: " + e);
    }
    return false;
  }

  
}

class Producer implements Runnable
{
  
  private BoundedBuffer buffer;
  public boolean check;
  public int count;
  public int i;
  int sleeppro;
	
  
  public Producer(BoundedBuffer buff, int sleep)
	{
  	buffer = buff;
  	i = 0;
    sleeppro = sleep;
  }

  public void run()
	{

    Random rand = new Random();
    while(i < 100)
		{
     try{
      int gotosleep = rand.nextInt(sleeppro);
      Thread.sleep(gotosleep);
    }
    catch(InterruptedException e){

    }
      count = buffer.count;
      while(count == 5)
			{
        System.out.printf("");
      }
     
      if(buffer.insert(rand.nextInt(1000)))
			{
        i++;
      }
			
    }
  }
}

class Consumer implements Runnable{
	
  
  private BoundedBuffer buffer;
  public boolean check;
  public int count;
  public int i;
  int sleepcon;
	
  
  public Consumer(BoundedBuffer buff,int sleep)
	{
    i = 0;
    buffer = buff;
    sleepcon = sleep;
  }

  public void run()
	{
		Random rand = new Random();
    while(i < 100)
		{
      try{
      int gotosleep = rand.nextInt(sleepcon);
      Thread.sleep(gotosleep);
    }
    catch(InterruptedException e){

    }
      count = buffer.count;
      
      while(count == 0)
			{
        System.out.printf("");
        count = buffer.count;
      }
     
      if(buffer.remove())
			{
        i++;
      }
    }
  }
}

