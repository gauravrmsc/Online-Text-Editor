package com.crio.qcharm.ds;

import com.crio.qcharm.request.PageRequest;
import com.crio.qcharm.request.SearchRequest;
import java.util.List;

public interface SourceFileVersion {


  List<String> getAllLines();

  Page getLinesBefore(PageRequest pageRequest);

  Page getLinesAfter(PageRequest pageRequest);

  Page getLinesFrom(PageRequest pageRequest);

  List<Cursor> getCursors(SearchRequest searchRequest);



}
