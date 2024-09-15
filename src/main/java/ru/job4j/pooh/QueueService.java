package ru.job4j.pooh;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class QueueService implements Service {

    private final ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> queues = new ConcurrentHashMap<>();

    private final static String GET = "GET";

    private final static String POST = "POST";

    @Override
    public Resp process(Req req) {
        String text = "";
        String status = "";
        if (POST.equals(req.httpRequestType())) {
            if (queues.get(req.getSourceName()) == null) {
                ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();
                queue.offer(req.getParam());
                queues.putIfAbsent(req.getSourceName(), queue);
            } else {
                queues.get(req.getSourceName()).offer(req.getParam());
            }
            text = req.getParam();
            status = "200 OK";
        }
        if (GET.equals(req.httpRequestType())) {
             if (queues.get(req.getSourceName()) != null && !queues.get(req.getSourceName()).isEmpty())  {
                 text = queues.get(req.getSourceName()).poll();
                 status = "200 OK";
             } else {
                 status = "204 No Content";
             }
        }
        Resp rsl = new Resp(text, status);
        return rsl;
    }

}
