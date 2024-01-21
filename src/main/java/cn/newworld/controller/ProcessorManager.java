package cn.newworld.controller;

import java.util.ArrayList;
import java.util.List;

public class ProcessorManager {
    private static final List<Processor> processors = new ArrayList<>();

    public static void registerProcessor(Processor processor){
        processors.add(processor);
    }

    public static List<Processor> getProcessors(){
        return processors;
    }

    public static void  close(){
        processors.clear();
    }
}
