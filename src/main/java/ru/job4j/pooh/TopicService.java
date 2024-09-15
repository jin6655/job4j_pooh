package ru.job4j.pooh;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TopicService implements Service {

    private final ConcurrentHashMap<String, ConcurrentHashMap<String, ConcurrentLinkedQueue<String>>> queues = new ConcurrentHashMap<>();

    private final static String GET = "GET";

    private final static String POST = "POST";

    @Override
    public Resp process(Req req) {
        String text = "";
        String status = "";
        if (POST.equals(req.httpRequestType())) {
            if (queues.get(req.getSourceName()) == null) {
                status = "204 No Content";
            } else {
                for (Map.Entry<String, ConcurrentLinkedQueue<String>> entry: queues.get(req.getSourceName()).entrySet()) {
                    entry.getValue().offer(req.getParam());
                }
                text = req.getParam();
                status = "200 OK";
            }
        }
        if (GET.equals(req.httpRequestType())) {
            if (queues.get(req.getSourceName()) != null &&
                    queues.get(req.getSourceName()).get(req.getParam()) != null &&
                    !queues.get(req.getSourceName()).get(req.getParam()).isEmpty()) {
                text = queues.get(req.getSourceName()).get(req.getParam()).poll();
                status = "200 OK";
            } else if (queues.get(req.getSourceName()) == null) {
                ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> map = new ConcurrentHashMap<>();
                map.putIfAbsent(req.getParam(), new ConcurrentLinkedQueue<>());
                queues.putIfAbsent(req.getSourceName(), map);
                status = "204 No Content";
            } else if (queues.get(req.getSourceName()).get(req.getParam()) == null) {
                queues.get(req.getSourceName()).putIfAbsent(req.getParam(), new ConcurrentLinkedQueue<>());
                status = "204 No Content";
            } else if (queues.get(req.getSourceName()).get(req.getParam()).isEmpty()) {
                status = "204 No Content";
            }
        }
        Resp rsl = new Resp(text, status);
        return rsl;
    }

}
