package org.gsep.manager;

import java.awt.List;
import java.util.ArrayList;

class Holder {
    String name;
    ArrayList<String> subEventTypes = new ArrayList<String>();
    String hashKey;
    double time;
    ArrayList<Double> subEventSplits = new ArrayList<Double>();
    double endTime;
}

public class MapperClass{
	ArrayList<Holder> list = new ArrayList<Holder>();
}
