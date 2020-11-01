package org.layz.hx.core.util.factory;

import org.layz.hx.core.support.schedule.JobResultHandler;
import org.layz.hx.core.support.schedule.JobResultHandlerDirector;
import org.layz.hx.core.support.schedule.JobResultHandlerImpl;

public class JobResultHandlerFactory {
    private JobResultHandlerFactory(){}
    private static JobResultHandlerDirector jobResultHandler = new JobResultHandlerDirector();

    /**
     * 设置处理器
     * @param handler
     */
    public static void setHandler(JobResultHandler handler){
        jobResultHandler.setJobResultHandler(handler);
    }

    /**
     * 获取处理器
     * @return
     */
    public static JobResultHandler getHandler(){
        if(null == jobResultHandler.getJobResultHandler()) {
            jobResultHandler.setJobResultHandler(new JobResultHandlerImpl());
        }
        return jobResultHandler;
    }

}
