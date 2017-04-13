/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agendamanager;

/**
 *
 * @author naimisha manikonda
 */
import java.io.*;
import java.net.*;
import java.util.*;
import java.lang.*;
import java.io.File.*;
public class Agendamanager {

    /**
     * @param args the command line arguments
     */
    public int cycle = 0;
    
    StringTokenizer st=null;
    StringTokenizer st1=null;
    String name;
    int value;
    static ArrayList<Integer> valuelist=new ArrayList<Integer>();//a new array list for storing priorities
    static ArrayList<String> instlist=new ArrayList<String>();//for storing name of rule
   int largest=0; 
    int length;
    public String[] input;
         public int arrsize;
	 long Start;
	 long End;
    
    
    public  Agendamanager() throws FileNotFoundException, IOException
    {
            try
            {//for reading path from the user and reading rules from the filefile
                Scanner scanner = new Scanner (System.in);
                System.out.println("enter the path  of the file which has the rules");
  
		String path = scanner.nextLine();
		Start = System.currentTimeMillis();
		
		File data =new File(path);
		
		// Existing file check
                    if (!(data.exists()))
                     {
			System.out.println("File specified is not found in the path specified");
			return;
                     }
		
                      FileInputStream fis= new FileInputStream(path);
                      FileReader fileReader = new FileReader(path);
                      BufferedReader in = new BufferedReader(fileReader);
  
                      String inline;
                      String line1;
                      char n;
                            while ((inline = in.readLine()) != null)   
                            {
                                st=new StringTokenizer(inline,")");
                                        while(st.hasMoreTokens())
                                                        {
                                                             line1=st.nextToken(); 
                                                             line1=line1.replaceAll("\\s+", "");
                                                             line1=line1.replaceAll("\\(", "");
                                                             n=line1.charAt(0);
                                                                if(n==','){
                                                                            line1=line1.substring(1);
                                                                            st1=new StringTokenizer(line1,",");
                                                                            name = st1.nextToken();
                                                                            value =Integer.parseInt(st1.nextToken());
                                                                            Insert(name,value);
                                                                                 
                                                                             } 
                                                                else {
                                                                             st1=new StringTokenizer(line1,",");
                                                                            name = st1.nextToken();
                                                                            value =Integer.parseInt(st1.nextToken());
                                                                            Insert(name,value);
                                        
                                                                      } 
                                    
                                                        }
                                        buildheap(valuelist);
                                        heap_extract_max();
                             }
                       int len=instlist.size();
                        for(int i=1;i<=len;i++)
                            {
                                heap_extract_max();
                            }
	 End = System.currentTimeMillis();//finding the end time
          System.out.println("The process started at "+Start);
	 System.out.println("inference ends at cycle"+cycle);
	 double time_diff = End - Start;//fimding the time taken 
	 double elapsed_time = time_diff/1000;
          System.out.println(" time differrence"+time_diff);
	 System.out.println(" total time taken to execute"+elapsed_time+" Secs");
  		//Close the input stream
  		in.close();
		
		//input = lines.toArray(new String[lines.size()]);		 
            }
            catch(FileNotFoundException e)
            {
    
                System.out.println("file not found");
            }
    
    
  
    
    }

    
    public void heap_extract_max()
	{           
		int max=0;
		int maximum;
		
                      
		if(valuelist.size()>=1)	
                {
                    if(cycle<30){
                        length= valuelist.size()+1;
			 maximum=valuelist.get(0);
                         //System.out.println(instlist.get(length));
			cycle++;
			System.out.println("\n ------ cycle number "+cycle+"---- ");
			System.out.println("contents of   Agenda manager in the cycle");
			
			for (int i=0;i<valuelist.size();i++){
				System.out.print("("+instlist.get(i)+","+valuelist.get(i)+")");
				if (i<(valuelist.size()-1))
					System.out.print("\n");
			}
			System.out.println("\n the rule deleted is "+instlist.get(max)+" and the priority is "+maximum);
                        heap_remove(max) ;
			heapify(valuelist,0);
                }	
                else
                    {
                    return;
                    }
                
                }
				
		else
                {
                System.out.println("heap is empty no more instlists to execute");
                }
			 
		}
		
				
	
 


    

    public void heap_remove(int max) {
     
        if(length==0){
        
            System.out.println("heap empty");
        
        }
        else{
        //size = valuelist.size()-1;
        valuelist.remove(max);
        instlist.remove(max);
        length = length-1;
        }    
	 	
    }

 
 
    public void heapify(ArrayList<Integer> valuelist, int i)
    {
    int left = leftChild(i);    // index of node i's left child
		int right = rightChild(i);  // index of node i's right child
		int largest;    // will hold the index of the node with the smallest element
		// among node i, left, and right

                
            
                if (left < valuelist.size()-1 &&(valuelist.get(left)>valuelist.get(i)) )
			largest = left;   // yes, so the left child is the largest so far
		else
			largest = i;      // no, so node i is the largest so far

		if (right < valuelist.size()-1 && (valuelist.get(right)>valuelist.get(i)) )
			largest = right;  
		if (largest != i) {
			swap(valuelist, i, largest);
			//heapify(valuelist,largest);
		}
    
    
    
    
    }
    private static int leftChild(int i) {
		return 2*i + 1;
	}
    private static int rightChild(int i) {
		return 2*i + 2;
	}
    public void buildheap(ArrayList<Integer> valuelist)
    {
    length = valuelist.size();
    for(int i=(length/2);i>=0;i--)
    {
    
    heapify(valuelist,i);
    
    }
    
    }

    
 public void Insert(String name,int value)
    {
    
valuelist.add(value); 
instlist.add(name);// Put new value at end;
		 int i = valuelist.size()-1;  // and get its location

		// Swap with parent until parent not larger
		while (i > 0 && (valuelist.get(i)>valuelist.get(parent(i)))) {
			
			swap(valuelist, i, parent(i));
			i = parent(i);
		}
    
    
    
    
    }
 


    private static <E> void swap(ArrayList<E> valuelist, int i, int j) {
		E t = valuelist.get(i);
		valuelist.set(i, valuelist.get(j));
		valuelist.set(j, t);
                
                String temp;
                 temp = instlist.get(i);
                instlist.set(i, instlist.get(j));
		instlist.set(j, temp);
                
	}
      private static int parent(int i) {
		return (i-1)/2;
            }
      
        public static void main(String[] args) throws IOException {
       Agendamanager a=new Agendamanager();
        
    }
    
}
