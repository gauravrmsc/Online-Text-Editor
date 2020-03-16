package com.crio.qcharm.ds;

import com.crio.qcharm.request.PageRequest;
import com.crio.qcharm.request.SearchRequest;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class SourceFileVersionLinkedListImpl implements SourceFileVersion {

  List<String> allLines = new LinkedList<>();
  String fileName;

  SourceFileVersionLinkedListImpl(FileInfo fileInfo) {
  }

  public SourceFileVersionLinkedListImpl(SourceFileVersionLinkedListImpl obj) {
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
        assert (oneEdit instanceof SearchReplace);
        apply((SearchReplace) oneEdit);
      }
    }
    return this;
  }

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
