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
  }









  @Override
  public void editLines(EditRequest editRequest) {
  }










}
