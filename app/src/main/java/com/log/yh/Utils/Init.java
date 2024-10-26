package com.log.yh.Utils;


public class Init {
    private ClassLoader mClassLoader;
    public void setClassloader(ClassLoader mClassLoader){
      this.mClassLoader=mClassLoader;
    }
    public ClassLoader getClassloader() throws Throwable {
        if (mClassLoader==null){
            throw new Exception();
        }
        return mClassLoader;
    }
}
