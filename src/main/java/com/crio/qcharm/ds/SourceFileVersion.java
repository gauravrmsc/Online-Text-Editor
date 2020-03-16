package com.crio.qcharm.ds;

import com.crio.qcharm.request.PageRequest;
import com.crio.qcharm.request.SearchRequest;
import java.util.List;

public interface SourceFileVersion {

  SourceFileVersion apply(List<Edits> edits);

  void apply(SearchReplace searchReplace);

  void apply(UpdateLines updateLines);

  List<String> getAllLines();

  Page getLinesBefore(PageRequest pageRequest);

  Page getLinesAfter(PageRequest pageRequest);

  Page getLinesFrom(PageRequest pageRequest);

  //TODO:
  // You are given searchPattern return.
  // Return starting cursorAt position for all string occurance

  List<Cursor> getCursors(SearchRequest searchRequest);

  //TODO:
  // You are given searchPattern and replacePattern.
  // Replace every occurance for searchPattern by replacePattern


}
