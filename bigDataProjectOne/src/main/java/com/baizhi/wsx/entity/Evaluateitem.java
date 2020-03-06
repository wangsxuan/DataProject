package com.baizhi.wsx.entity;

public class Evaluateitem {
    private String componentName;
    private Boolean value;

    public String getComponentName() {
        return componentName;
    }

    public void setComponentName(String componentName) {
        this.componentName = componentName;
    }

    public Boolean getValue() {
        return value;
    }

    public void setValue(Boolean value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Evaluateitem{" +
                "componentName='" + componentName + '\'' +
                ", value=" + value +
                '}';
    }
}
