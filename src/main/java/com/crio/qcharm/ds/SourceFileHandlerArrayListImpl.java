package com.crio.qcharm.ds;

import com.crio.qcharm.request.EditRequest;
import com.crio.qcharm.request.PageRequest;
import com.crio.qcharm.request.SearchReplaceRequest;
import com.crio.qcharm.request.SearchRequest;
import com.crio.qcharm.request.UndoRequest;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class SourceFileHandlerArrayListImpl implements SourceFileHandler {
  SourceFileVersionArrayListImpl sourceFileVersionArrayListImpl;

  public SourceFileHandlerArrayListImpl(String fileName) {
  }



  // TODO: CRIO_TASK_MODULE_LOAD_FILE
  // Input:
  //      FileName
  //  Steps:
  //    1. Given SourceFile name get the latest version of the it.
  //  Description:
  //    After loading the file the SourceFile would have gone through multiple
  //    changes. When we say "Latest version of the SourceFile" it means the SourceFile's present
  //    view after applying all the changes.


  // TODO: CRIO_TASK_MODULE_LOAD_FILE
  // Input:
  //      FileInfo
  //  Steps:
  //    1. Create an object of the SourceFileVersionArrayListImpl class using the given fileInfo.
  //    2. Using that object get the first 50 lines of this file.
  //    3. Create Page object using the lines received and return the same.
  //  How to construct Page object ?
  //    1. lines should be the first 50 lines of the SourceFile
  //    2. cursorAt should be new Cursor(0,0)
  //    3. StartingLineNo is set to 0
  //    4. fileName should be same as the fileInfo.fileName
  //
  //  What is Cursor?
  //     It represents position of the cursor in the editor.
  //     Cursor is represented using (lineNumber, columnNumber).

  @Override
  public Page loadFile(FileInfo fileInfo) {
    Cursor cursorAt = new Cursor(0,0);
    PageRequest pageRequest = new PageRequest(-1,fileInfo.getFileName(),50,cursorAt);
    sourceFileVersionArrayListImpl = 
        new SourceFileVersionArrayListImpl(fileInfo);
    Page page = 
        sourceFileVersionArrayListImpl.getLinesAfter(pageRequest);
    return page;    
  }

  // TODO: CRIO_TASK_MODULE_LOAD_FILE
  // Input:
  //     PageRequest - contains following information
  //         1. Starting line number
  //         2. File name;
  //         3. requested number of Lines
  //         4. Cursor position
  //  Steps:
  //    1. After loadFile SourceFileVersionArrayListImpl has all the file information
  //    2. Using that get "requested number of lines" above "the given line number".
  //    3. Construct Page object and return
  //  How to construct Page object ?
  //    1. lines - lines you got in step 2
  //    2. cursorAt should be same as pageRequest.cursorAt
  //    3. StartingLineNo should be same as first line number of lines
  //    4. fileName should be same as the pageRequest.fileName

  @Override
  public Page getPrevLines(PageRequest pageRequest) {
    //List<String> lines = sourceFileVersionArrayListImpl.lines;
    //Cursor cursor = new Cursor(pageRequest.getStartingLineNo(),0);
    //pageRequest.setCursorAt(cursor);
    Page page = sourceFileVersionArrayListImpl.getLinesBefore(pageRequest);
    return page;
  }

  // TODO: CRIO_TASK_MODULE_LOAD_FILE
  // Input:
  //     PageRequest - contains following information
  //         1. Starting line number
  //         2. File name;
  //         3. requested number of Lines
  //         4. Cursor position
  //  Steps:
  //    1. After loadFile SourceFileVersionArrayListImpl has all the file information
  //    2. Using that get "requested number of lines" below "the given line number".
  //    3. Construct Page object and return
  //  How to construct Page object ?
  //    1. lines - lines you got in step 2
  //    2. cursorAt should be same as pageRequest.cursorAt
  //    3. StartingLineNo should be same as first line number of lines
  //    4. fileName should be same as the pageRequest.fileName

  @Override
  public Page getNextLines(PageRequest pageRequest) {
    //Cursor cursor = new Cursor(pageRequest.getStartingLineNo(),0);
    //pageRequest.setCursorAt(cursor);
    Page page = sourceFileVersionArrayListImpl.getLinesAfter(pageRequest);
    return page;
  }

  // TODO: CRIO_TASK_MODULE_LOAD_FILE
  // Input:
  //     PageRequest - contains following information
  //         1. Starting line number
  //         2. File name;
  //         3. requested number of Lines
  //         4. Cursor position
  //  Steps:
  //    1. After loadFile SourceFileVersionArrayListImpl has all the file information
  //    2. Using the object get "requested number of lines" starting from "the given line number".
  //    3. Construct Page object and return
  //  How to construct Page object ?
  //    1. lines - lines you got in step 2
  //    2. cursorAt should be same be set to (startingLineNo, 0);
  //    3. StartingLineNo should be same as first line number of lines
  //    4. fileName should be same as the pageRequest.fileName

  @Override
  public Page getLinesFrom(PageRequest pageRequest) {
    Cursor cursor = new Cursor(pageRequest.getStartingLineNo(),0);
    pageRequest.setCursorAt(cursor);
    Page page = sourceFileVersionArrayListImpl.getLinesFrom(pageRequest);
    return page;
  }






  // TODO: CRIO_TASK_MODULE_SEARCH
  // Input:
  //     SearchRequest - contains following information
  //         1. pattern - pattern you want to search
  //         2. File name - file where you want to search for the pattern
  // Description:
  //    1. For the given SourceFile use SourceFileVersionArrayListImpl
  //    .getCursors() to find all occurrences of the pattern in the SourceFile.
  //    2. return the all occurrences starting position in a list.

  @Override
  public List<Cursor> search(SearchRequest searchRequest) {
    return sourceFileVersionArrayListImpl.getCursors(searchRequest);
  }









  @Override
  public void editLines(EditRequest editRequest) {
  }

  @Override
  public SourceFileVersion getLatestSourceFileVersion(String fileName) {
    return null;
  }










}
