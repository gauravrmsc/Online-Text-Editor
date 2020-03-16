package com.crio.qcharm.ds;

import com.crio.qcharm.request.EditRequest;
import com.crio.qcharm.request.PageRequest;
import com.crio.qcharm.request.SearchReplaceRequest;
import com.crio.qcharm.request.SearchRequest;
import com.crio.qcharm.request.UndoRequest;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

public class SourceFileHandlerLinkedListImpl implements SourceFileHandler {


  public SourceFileHandlerLinkedListImpl(String fileName) {
  }



  // TODO: CRIO_TASK_MODULE_IMPROVING_EDITS
  // Input:
  //      FileInfo
  //  Steps:
  //    1. create an object of the SourceFileVersionLinkedListImpl class using fileInfo
  //    2. Get the first 50 lines of this file(file to be uploaded)
  //    3. You need to create Page object using the lines received and return the same.

  @Override
  public SourceFileVersion getLatestSourceFileVersion(String fileName) {
    // FIXME - incomplete implementation
    return latest;
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


  @Override
  public void setCopyBuffer(CopyBuffer copyBuffer) {
    this.copyBuffer = copyBuffer;
  }

  @Override
  public CopyBuffer getCopyBuffer() {
    CopyBuffer lastCopyBuffer = this.copyBuffer;
    this.copyBuffer = new CopyBuffer(new LinkedList<>());
    return lastCopyBuffer;
  }

  @Override
  public SourceFileVersion cloneObj(SourceFileVersion ver) {
    return new SourceFileVersionLinkedListImpl((SourceFileVersionLinkedListImpl) ver);
  }

  @Override
  public void editLines(EditRequest editRequest) {
  }


}
