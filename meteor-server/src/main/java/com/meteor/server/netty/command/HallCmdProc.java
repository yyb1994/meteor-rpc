package com.meteor.server.netty.command;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.meteor.common.exception.CommonException;
import com.meteor.common.network.protocol.PacketBase;
import com.meteor.server.netty.session.HallSession;


/**
 * @author SuperMu
 * @description 协议处理
 * @Date: 2018/7/26  15:52D
 */
public class HallCmdProc {
    private static final Log log = LogFactory.get(HallCmdProc.class);

    public static class SingletonHolder{
        private static final HallCmdProc INSTANCE=new HallCmdProc();
    }

    public static HallCmdProc getInstance(){
        return SingletonHolder.INSTANCE;
    }

    private HallCmdProc() {

    }


    public void handleMsg(PacketBase packet, HallSession session) {
        byte packetType = packet.getPacketType();


        try {

        } catch (CommonException e) {
            log.error(e);
        } catch (Exception e) {
            log.error(e);
        }


    }


}
