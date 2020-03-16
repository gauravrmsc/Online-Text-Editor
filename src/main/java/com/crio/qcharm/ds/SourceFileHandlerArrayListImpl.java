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


  public SourceFileHandlerArrayListImpl(String fileName) {
  }






  @Override
  public Page loadFile(FileInfo fileInfo) {
  }


  @Override
  public Page getPrevLines(PageRequest pageRequest) {
  }


  @Override
  public Page getNextLines(PageRequest pageRequest) {
  }


  @Override
  public Page getLinesFrom(PageRequest pageRequest) {
  }


  @Override
  public List<Cursor> search(SearchRequest searchRequest) {
  }

  // TODO: CRIO_TASK_MODULE_CUT_COPY_PASTE
  // Input:
  //     CopyBuffer - contains following information
  //         1. List of lines
  // Description:
  //      Store the incoming copy buffer

  @Override
  public void setCopyBuffer(CopyBuffer copyBuffer)
  {
  }

  // TODO: CRIO_TASK_MODULE_CUT_COPY_PASTE
  // Input:
  //      None
  // Description:
  //      return the previously stored copy buffer
  //      if nothing is stored return copy buffer containing empty lines.

  @Override
  public CopyBuffer getCopyBuffer() {
  }

  // TODO: CRIO_TASK_MODULE_CUT_COPY_PASTE
  // Input:
  //      Object of type SourceFileVersionArrayListImpl
  // Description:
  //      make a copy of the the given SourceFileVersionArrayListImpl object return new object
  // NOTE:
  //      DON'T CHANGE THE SIGNATURE OF THIS FUNCTION

  @Override
  public SourceFileVersion cloneObj(SourceFileVersion ver) {
  }

  // TODO: CRIO_TASK_MODULE_CUT_COPY_PASTE
  // Input:
  //     EditRequest
  //        1. starting line no - starting line number of last time it received page from backend
  //        2. ending line no - ending line no of the last time it received page from backend;
  //        3. new content - list of lines present view of lines(starting line no, ending line no)
  //        4. file name
  //        5. cursor
  // Description:
  //        1. Remove the line numbers in the range(starting line no, ending line no)
  //        2. Inserting the lines in new content starting position starting line no
  // Example:
  //        EditRequest looks like this
  //            1. start line no - 50
  //            2. ending line no - 60
  //            3. new content - ["Hello world"]
  //
  //       Assume the file has 100 lines in it
  //
  //       File contents before edit:
  //       ==========================
  //       line no 1
  //       line no 2
  //          .....
  //       line no 100
  //
  //        File contents After Edit:
  //        =========================
  //        line no 1
  //        line no 2
  //        line no 3
  //         .....
  //        line no 49
  //        Hello World
  //        line no 61
  //        line no 62
  //          ....
  //        line no 100
  //


  @Override
  public void editLines(EditRequest editRequest) {
  }










}
