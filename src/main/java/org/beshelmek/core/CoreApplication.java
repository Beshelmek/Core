package org.beshelmek.core;

import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoEventType;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.beshelmek.core.api.network.PacketFactory;
import org.beshelmek.core.dao.UserDAO;
import org.beshelmek.core.network.ConnectionHandler;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.net.InetSocketAddress;

public class CoreApplication {
    public static CoreApplication INSTANCE;
    public static ApplicationContext CONTEXT;

    private NioSocketAcceptor acceptor;

    public CoreApplication(){
        this.acceptor = new NioSocketAcceptor(Runtime.getRuntime().availableProcessors() + 1);
        //this.acceptor.getFilterChain().addLast("whitelist", (IoFilter)this.whitelist);
        this.acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new PacketFactory()));
        this.acceptor.getFilterChain().addLast("readPool", new ExecutorFilter(1, 2, IoEventType.MESSAGE_RECEIVED));
        this.acceptor.getFilterChain().addLast("writePool", new ExecutorFilter(1, 2, IoEventType.WRITE));
        this.acceptor.setHandler(new ConnectionHandler());
        this.acceptor.getSessionConfig().setMinReadBufferSize(32);
        this.acceptor.getSessionConfig().setReadBufferSize(2048);
        this.acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);

        try {
            this.acceptor.bind(new InetSocketAddress(9123));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        CONTEXT = new AnnotationConfigApplicationContext(ApplicationContextConfiguration.class);
        //INSTANCE = new CoreApplication();

        UserDAO userDAO = CONTEXT.getBean(UserDAO.class);

        userDAO.getByID(1).ifPresent(user -> {
            user.setEmail("votyakov1404@yandex.ru");

            System.out.println(user.toString());

            userDAO.save(user);
        });
    }
}
