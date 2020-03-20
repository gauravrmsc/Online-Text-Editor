package com.crio.qcharm.ds;

import com.crio.qcharm.request.PageRequest;
import com.crio.qcharm.request.SearchRequest;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class SourceFileVersionLinkedListImpl implements SourceFileVersion {




  SourceFileVersionLinkedListImpl(FileInfo fileInfo) {
  }

  public SourceFileVersionLinkedListImpl() {
  }

  public SourceFileVersionLinkedListImpl(SourceFileVersionLinkedListImpl obj) {
  }


  @Override
  public SourceFileVersion apply(List<Edits> edits) {
    List<String> lines = new LinkedList<>();
    lines.addAll(lines);

    SourceFileVersionLinkedListImpl latest = new SourceFileVersionLinkedListImpl();

    for (Edits oneEdit : edits) {
      if (oneEdit instanceof UpdateLines) {
        apply((UpdateLines) oneEdit);
      } else {
        assert (oneEdit instanceof SearchReplace);
        apply((SearchReplace) oneEdit);
      }
    }
    return this;
  }


  // TODO: CRIO_TASK_MODULE_SEARCH_REPLACE
  // Input:
  //    SearchReplace
  //          1. pattern - pattern to be found
  //          2. newPattern - pattern to be replaced with
  //  Description:
  //      Find every occurrence of the pattern and replace it newPattern.

  @Override
  public void apply(SearchReplace searchReplace) {
  }


  @Override
  public void apply(UpdateLines updateLines) {
  }

  @Override
  public List<String> getAllLines() {
  }

  @Override
  public Page getLinesBefore(PageRequest pageRequest) {
  }


  @Override
  public Page getLinesAfter(PageRequest pageRequest) {
  }


  @Override
  public Page getLinesFrom(PageRequest pageRequest) {
  }


  @Override
  public List<Cursor> getCursors(SearchRequest searchRequest) {
  }


}
