package org.layz.hx.persist.util;

import org.layz.hx.base.pojo.Page;
import org.layz.hx.base.pojo.Pageable;
import org.layz.hx.core.visitor.SqlDataVisitor;
import org.layz.hx.core.visitor.Visitor;
import org.layz.hx.persist.service.FindPageInfo;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class BackDataUtil<T> implements AutoCloseable{
    private final OutputStream os;
    private int page;
    private int size = 2000;
    private T param;
    private FindPageInfo<T> findPageInfo;
    private Visitor visitor;

    public BackDataUtil(String path, FindPageInfo<T> findPageInfo) throws IOException {
        File file = new File(path);
        File parentFile = file.getParentFile();
        if(!parentFile.exists()) {
            parentFile.mkdirs();
        }
        this.os = new FileOutputStream(file);
        this.findPageInfo = findPageInfo;
    }

    public BackDataUtil(OutputStream os,FindPageInfo<T> findPageInfo)  {
        this.os = os;
        this.findPageInfo = findPageInfo;
    }

    public void excute() throws IOException{
        this.page = 1;
        if(null == visitor) {
            visitor = new SqlDataVisitor();
        }
        boolean flag = backPage();
        while (flag) {
            this.page ++;
            flag = backPage();
        }
    }

    private boolean backPage() throws IOException {
        Page<T> pageData = findPageInfo.findPage(param, new Pageable(page, size));
        if(null == pageData || CollectionUtils.isEmpty(pageData.getData())) {
            return false;
        }
        List<T> data = pageData.getData();
        StringBuilder sql = new StringBuilder((String) visitor.begin(data.get(0))).append("\n");
        for (int i = 0; i < data.size(); i++) {
            if (i > 0) {
                sql.append(",\n");
            }
            sql.append(visitor.accept(data.get(i)));
        }
        String substring = sql.append(";\n\n").toString();
        this.os.write(substring.getBytes("utf-8"));
        this.os.flush();
        return true;
    }

    public void setParam(T param) {
        this.param = param;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setVisitor(Visitor visitor) {
        this.visitor = visitor;
    }

    @Override
    public void close() throws Exception {
        if(null != this.os) {
            this.os.flush();
            this.os.close();
        }
    }
}
