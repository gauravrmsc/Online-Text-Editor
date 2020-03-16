package com.crio.qcharm.runner;
import com.crio.qcharm.ds.RunCodeOutput;
import java.util.List;

/*
 *  DONT'T EDIT
 */

public class PythonRunner extends BaseRunner {


  public RunCodeOutput runProgram(List<String> filesToDelete, String fileName,
      String[] inputs) throws Exception {
    String runProcess = "timeout 5 python3 " + fileName;

    StringBuilder outputBuilder = new StringBuilder();
    outputBuilder.append(callProcess(runProcess, inputs));

    RunCodeOutput output = new RunCodeOutput();
    output.setOutput(outputBuilder.toString());
    return output;
  }
}
