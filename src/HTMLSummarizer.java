import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Stack;

public class HTMLSummarizer implements Tester
{
	// method to provide the outline of the HTML document
	public ArrayList<String> compute(Scanner input)
	{
		// Create an object of HTMLScanner for reading HTML tags
		HTMLScanner inputScanner = new HTMLScanner(input);
		
		// create a Stack to store the tags
		Stack<String> tagStack = new Stack<>();
		// create an ArrayList to store the final result
		ArrayList<String> result = new ArrayList<>();
		// define another Stack to keep track of the count of the inner elements
		Stack<Integer> countStack = new Stack<>();
		
		// variable to prepend the indentation
		String indentation = "";
		// boolean flag to check if last tag was a void tag
		boolean isPreviousVoid = false;

		// read the tag
		while (inputScanner.hasNextTag())
		{
			// get current tag
			String tagValue = inputScanner.nextTag().trim();
			
			// if the tag starts with ! then ignore the tag and get the next tag
			if (tagValue.charAt(0) == '!')
				continue;
			
			// if an end tag contains '/' as the first character then remove it
			if (tagValue.charAt(0) == '/')
			{
				tagValue = tagValue.substring(1);
			}

			// check if top of stack is equal to the current tag read from the
			// scanner
			if (!tagStack.empty() && tagStack.peek().equals(tagValue))
			{
				// if a tag is going to be popped from the Stack then assign the corresponding
				// count to the tag which is being popped
				// use a loop to traverse the list in reverse and then match the tag which
				// is being popped
				for (int index = result.size() - 1; index >= 0; index--)
				{
					// get the top of the stack
					String current = tagStack.peek();
					
					// check for a match
					if (result.get(index).endsWith(current))
					{
						// if a match is found in the list then add the count to the end of the tag
						result.set(index, result.get(index) + " " + countStack.peek());
					}
				}
				
				// remove top of the stack from the stack that contains tags
				tagStack.pop();
				// remove top of the stack from the stack that contains count
				countStack.pop();
				// decrement the indentation
				indentation = indentation.substring(0, indentation.length() - 1);
			}
			// if top of stack is not the matching tag then add this tag to the stack
			else
			{
				// check whether it is a void tag or not
				if (Arrays.binarySearch(HTMLScanner.voidTags, tagValue) < 0)
				{
					// if not a void tag then add it to the Stack
					tagStack.push(tagValue);
					// check the countStack, if it is not empty then increment the value stored at the top of the Stack
					if (!countStack.empty())
					{
						int top = countStack.peek();
						countStack.pop();
						countStack.push(top + 1);
					}
					
					// add one more count value to the countStack
					countStack.push(0);
					// set isPreviousVoid to false as the previous tag is not void
					isPreviousVoid = false;
				}
				else
				{
					// since the current tag is void then set the isPreviousVoid to true
					isPreviousVoid = true;
					
					// increment the top of the Stack count
					if (!countStack.empty())
					{
						int top = countStack.peek();
						countStack.pop();
						countStack.push(top + 1);
					}
				}
				
				// add more indentation
				indentation = indentation + ' ';
				
				// add the current tag with the indentation to the result list
				result.add(indentation + tagValue);
				
				// check if isPreviousVoid is set to true. If set to true then remove one space from the indentation 
				if (isPreviousVoid)
					indentation = indentation.substring(0, indentation.length() - 1);
			}
		}
		
		// reduce one indentation from all the tags
		for (int index = 0; index < result.size(); index++)
		{
			result.set(index, result.get(index).substring(1));
		}
		
		// return the result
		return result;
	}

	public static void main(String[] args)
	{
		// create a new Scanner object
		Scanner inputScanner;
		try
		{
			// read input from file
			inputScanner = new Scanner(new FileReader("file.txt"));
			
			// create a HTMLSummarizer object
			HTMLSummarizer htmlSumm = new HTMLSummarizer();
			
			// call compute method
			ArrayList<String> tagList = htmlSumm.compute(inputScanner);
			
			// display the result
			for (String current : tagList)
				System.out.println(current);

		}
		catch (FileNotFoundException e)
		{
			System.out.println("File not Found!");
		}
	}
}