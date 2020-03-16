package com.crio.qcharm.ds;

import com.crio.qcharm.request.PageRequest;
import com.crio.qcharm.request.SearchRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SourceFileVersionArrayListImpl implements SourceFileVersion {


  public SourceFileVersionArrayListImpl(SourceFileVersionArrayListImpl obj) {
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

  // TODO: CRIO_TASK_MODULE_CUT_COPY_PASTE
  // Input:
  //    SearchReplace
  //          1. pattern - pattern to be found
  //          2. newPattern - pattern to be replaced with
  //  Description:
  //      Find every occurrence of the pattern and replace it newPattern.

  @Override
  public void apply(SearchReplace searchReplace) {
  }


  // TODO: CRIO_TASK_MODULE_CUT_COPY_PASTE
  // Input:
  //     UpdateLines
  //        1. startingLineNoe - starting line number of last time it received page from backend
  //        2. numberOfLines - number of lines received from backend last time.
  //        3. lines - present view of lines in range(startingLineNo,startingLineNo+numberOfLines)
  //        4. cursor
  // Description:
  //        1. Remove the line numbers in the range(starting line no, ending line no)
  //        2. Inserting the lines in new content starting position starting line no
  // Example:
  //        UpdateLines looks like this
  //            1. start line no - 50
  //            2. length - 10
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



  @Override
  public List<Cursor> getCursors(SearchRequest searchRequest) {
    boolean efficient = true;
  }



  @Override
  public List<String> getAllLines() {
  }
}
