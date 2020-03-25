package com.crio.qcharm.ds;

import com.crio.qcharm.request.PageRequest;
import com.crio.qcharm.request.SearchRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class SourceFileVersionArrayListImpl implements SourceFileVersion {
  String fileName;
  List<String> lines;
  HashMap<String,List<Cursor>> hm = new HashMap<String,List<Cursor>>();

  
 









  // TODO: CRIO_TASK_MODULE_LOAD_FILE
  // Input:
  //     FileInfo - contains following information
  //         1. fileName
  //         2. List of lines
  // Steps:
  //     You task here is to construct SourceFileVersionArrayListImpl object by
  //     1. Storing the lines received from fileInfo object
  //     2. Storing the fileName received from fileInfo object.
  // Recommendations:
  //     1. Use Java ArrayList to store the lines received from fileInfo
 
  public SourceFileVersionArrayListImpl(FileInfo fileInfo) {
    this.fileName = fileInfo.fileName;
    this.lines = new ArrayList<String>(fileInfo.lines);
  }

  // TODO: CRIO_TASK_MODULE_LOAD_FILE
  // Input:
  //    1. lineNumber - The line number
  //    2. numberOfLines - Number of lines requested
  // Expected functionality:
  //    1. Get the requested number of lines starting before the given line number.
  //    2. Make page object of this and return.
  //    3. For cursor position use the value from pageRequest
  //    4. For fileName use the value from pageRequest
  // NOTE:
  //    If there less than requested number of lines, then return just those lines.
  //    Zero is the first line number of the file
  // Example:
  //    lineNumber - 50
  //    numberOfLines - 25
  //    Then lines returned is
  //    (line number 25, line number 26 ... , line number 48, line number49)


  public SourceFileVersionArrayListImpl(SourceFileVersionArrayListImpl obj) {
  }


  public SourceFileVersionArrayListImpl() {
  }

  @Override
  public SourceFileVersion apply(List<Edits> edits) {
    List<String> lines = new ArrayList<>();
    lines.addAll(lines);

    SourceFileVersionArrayListImpl latest = new SourceFileVersionArrayListImpl();

    for (Edits oneEdit : edits) {
      if (oneEdit instanceof UpdateLines) {
        apply((UpdateLines) oneEdit);
      } else {
        assert(oneEdit instanceof SearchReplace);
        apply((SearchReplace) oneEdit);
      }
    }
    return this;
  }


  @Override
  public void apply(SearchReplace searchReplace) {
  }


  // TODO: CRIO_TASK_MODULE_CUT_COPY_PASTE
  // Input:
  //     UpdateLines
  //        1. startingLineNo - starting line number of last time it received page from backend
  //        2. numberOfLines - number of lines received from backend last time.
  //        3. lines - present view of lines in range(startingLineNo,startingLineNo+numberOfLines)
  //        4. cursor
  // Description:
  //        1. Remove the line numbers in the range(starting line no, ending line no)
  //        2. Inserting the lines in new content starting position starting line no
  // Example:
  //        UpdateLines looks like this
  //            1. start line no - 50
  //            2. numberOfLines - 10
  //            3. lines - ["Hello world"]
  //
  //       Assume the file has 100 lines in it
  //
  //       File contents before edit:
  //       ==========================
  //       line no 0
  //       line no 1
  //       line no 2
  //          .....
  //       line no 99
  //
  //        File contents After Edit:
  //        =========================
  //        line no 0
  //        line no 1
  //        line no 2
  //        line no 3
  //         .....
  //        line no 49
  //        Hello World
  //        line no 60
  //        line no 61
  //          ....
  //        line no 99
  //



  @Override
  public void apply(UpdateLines updateLines) {
    List <String> newLines = updateLines.getLines();
    int start  =updateLines.getStartingLineNo();
    lines.addAll(start,newLines);
  }



  


  @Override
  public Page getLinesBefore(PageRequest pageRequest) {
    int lineNumber = pageRequest.getStartingLineNo();
    int numberOfLines = pageRequest.getNumberOfLines();
    int startPointer = lineNumber-numberOfLines;
    int start = (startPointer >=0 ) ? (startPointer) : 0;
    List<String> retLines = this.lines.subList(start,lineNumber);
    Page page = new Page(retLines,start,fileName,pageRequest.getCursorAt());
    return page;
  }

  // TODO: CRIO_TASK_MODULE_LOAD_FILE
  // Input:
  //    1. lineNumber - The line number
  //    2. numberOfLines - Number of lines requested
  // Expected functionality:
  //    1. Get the requested number of lines starting after the given line number.
  //    2. Make page object of this and return.
  //    3. For cursor position use the value from pageRequest
  //    4. For fileName use the value from pageRequest
  // NOTE:
  //    If there less than requested number of lines, then return just those lines.
  //    Zero is the first line number of the file  @Override
  // Example:
  //    lineNumber - 50
  //    numberOfLines - 25
  //    Then lines returned is
  //    (line number 51, line number 52 ... , line number 74, line number75)

  @Override
  public Page getLinesAfter(PageRequest pageRequest) {
    int lineNumber = pageRequest.getStartingLineNo();
    int numberOfLines = pageRequest.getNumberOfLines();
    int endPointer = lineNumber + numberOfLines + 1; 
    int start = lineNumber+1;
    int end = (lines.size() >= (endPointer))?(endPointer):lines.size();
    List<String> retLines = new ArrayList<String>();
    if (start<lines.size()) {
      retLines = lines.subList(start,end);
    }
    else {
      start = lineNumber;
    }
    Cursor cursor = pageRequest.getCursorAt();
    Page page = new Page(retLines,start,fileName,cursor);
    return page;
  }

  // TODO: CRIO_TASK_MODULE_LOAD_FILE
  // Input:
  //    1. lineNumber - The line number
  //    2. numberOfLines - Number of lines requested
  // Expected functionality:
  //    1. Get the requested number of lines starting from the given line number.
  //    2. Make page object of this and return.
  //    3. For cursor position should be (startingLineNo, 0)
  //    4. For fileName use the value from pageRequest
  // NOTE:
  //    If there less than requested number of lines, then return just those lines.
  //    Zero is the first line number of the file  @Override
  // Example:
  //    lineNumber - 50
  //    numberOfLines - 25
  //    Then lines returned is
  //    (line number 50, line number 51 ... , line number 73, line number74)

  @Override
  public Page getLinesFrom(PageRequest pageRequest) {
    int lineNumber = pageRequest.getStartingLineNo();
    int numberOfLines = pageRequest.getNumberOfLines();
    int endPointer = lineNumber + numberOfLines; 
    int end = (lines.size() >= (endPointer))?(endPointer):lines.size();
    List<String> retLines = lines.subList(lineNumber,end);
    int startingLineNo = lineNumber;
    Cursor cursor = pageRequest.getCursorAt();
    Page page = new Page(retLines,lineNumber,fileName,cursor);
    return page;
  }



  

  // TODO: CRIO_TASK_MODULE_IMPROVING_SEARCH
  // Input:
  //    SearchRequest - contains following information
  //        1. pattern - pattern you want to search
  //        2. File name - file where you want to search for the pattern
  // Description:
  //    1. Find all occurrences of the pattern in the SourceFile
  //    2. Create an empty list of cursors
  //    3. For each occurrence starting position add to the list of cursors
  //    4. return the list of cursors
  // Recommendation:
  //    1. Use FASTER string search algorithm.
  //    2. Feel free to try any other algorithm/data structure to improve search speed.
  // Reference:
  //     https://www.geeksforgeeks.org/kmp-algorithm-for-pattern-searching/


  /*@Override
  public List<Cursor> getCursors(SearchRequest searchRequest) {
    boolean efficient = true;
  }*/



  @Override
  public List<String> getAllLines() {
    return lines;
  }

  @Override
  public List<Cursor> getCursors(SearchRequest searchRequest) {
    List<Cursor> cursorList = new ArrayList<Cursor> ();
    String pattern = searchRequest.getPattern();
    int[] lps=createLPS(pattern);
    List<Cursor> resultFromCache = hm.get(pattern);
    if(resultFromCache!=null) {
      return resultFromCache;
    }
    for (int i=0;i<lines.size();i++) {
      search(lines.get(i),pattern,i,0,cursorList,lps);
   
    }
    hm.put(pattern,cursorList);
    return cursorList;
  }
 private void search(String line,String pattern,int lineNo,int startIndex,List<Cursor> cursorList,int[] lps){
 
  
  int lineLen = line.length();
  int patternLen  = pattern.length();
  if(lineLen<100) {
    if(line.contains(pattern)) {
      if(line.startsWith(pattern)){
      int position = line.indexOf(pattern);
      Cursor cursor = new Cursor(lineNo,position+startIndex);
      cursorList.add(cursor);}
      startIndex = startIndex +1;
      String subString = line.substring(1);
      search(subString,pattern,lineNo,startIndex,cursorList,null);
    }
    return;
  }

    int i = 0,j=0;
  while (i<lineLen) {
    
    if (line.charAt(i)==pattern.charAt(j)) {
      j++;
      i++;
    }
    else if(j!=0) {
      j = lps[j-1];
    } else {
      i++;
    } 
    if(j==patternLen) {
      int ptr = (i-pattern.length());
      Cursor cursor = new Cursor(lineNo, ptr);
      cursorList.add(cursor);
      j=0;
      
    }
  }
 
 }
 
 private int[] createLPS (String pattern){
  int len=pattern.length();
  int[] lps =new int[len];
  char[] arr=pattern.toCharArray();
  lps[0]=0;
  int i=0,j=1;
  while(j<len) {
    if(arr[i]==arr[j]) {
      i++;
      lps[j]=i;
      j++;
    }
    else if(i!=0) {
      i=lps[i-1];
    }
    else {
      lps[j]=i;
      j++;
    }
  }
return lps;
}
 
public Object clone() throws CloneNotSupportedException{
  return super.clone();
}

}
