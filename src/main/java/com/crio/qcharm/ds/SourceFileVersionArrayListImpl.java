package com.crio.qcharm.ds;

import com.crio.qcharm.request.PageRequest;
import com.crio.qcharm.request.SearchRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SourceFileVersionArrayListImpl implements SourceFileVersion {


  public SourceFileVersionArrayListImpl(SourceFileVersionArrayListImpl obj) {
  }












  public SourceFileVersionArrayListImpl(FileInfo fileInfo) {
  }



  @Override
  public Page getLinesBefore(PageRequest pageRequest) {
    int lineNumber = pageRequest.getStartingLineNo();
    int numberOfLines = pageRequest.getNumberOfLines();

  }


  @Override
  public Page getLinesAfter(PageRequest pageRequest) {
    int lineNumber = pageRequest.getStartingLineNo();
    int numberOfLines = pageRequest.getNumberOfLines();

  }


  @Override
  public Page getLinesFrom(PageRequest pageRequest) {
    int lineNumber = pageRequest.getStartingLineNo();
    int numberOfLines = pageRequest.getNumberOfLines();
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


  @Override
  public List<Cursor> getCursors(SearchRequest searchRequest) {
    boolean efficient = true;
  }



  @Override
  public List<String> getAllLines() {
  }
}
