package com.purefun.fstp.core.bo.tool;

public class RPCTool {
    public static byte[] subBytes(byte[] src, int begin, int count) {
        byte[] bs = new byte[count];
        for (int i=begin;i<begin+count; i++) bs[i-begin] = src[i];
        return bs;
    }
}
