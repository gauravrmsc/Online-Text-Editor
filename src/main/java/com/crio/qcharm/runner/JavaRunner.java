package com.crio.qcharm.runner;
import com.crio.qcharm.ds.RunCodeOutput;
import java.util.List;

/*
 *  DONT'T EDIT
 */

public class JavaRunner extends BaseRunner {


  @Override
  public RunCodeOutput runProgram(List<String> filesToDelete, String fileName,
      String[] inputs) throws Exception {
    String compileProcess = "javac " + fileName;
    String classFile = fileName.substring(0, fileName.length() - 5);
    filesToDelete.add(classFile + ".class");
    String runProcess = "timeout 5 java " + classFile;

    StringBuilder outputBuilder = new StringBuilder();
    outputBuilder.append(callProcess(compileProcess, new String[]{}));
    outputBuilder.append(callProcess(runProcess, inputs));

    RunCodeOutput output = new RunCodeOutput();
    output.setOutput(outputBuilder.toString());
    return output;
  }
}
