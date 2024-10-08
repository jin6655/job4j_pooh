package ru.job4j.pooh;

import org.junit.Test;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class QueueServiceTest {

    @Test
    public void whenPostThenGetQueue() {
        QueueService queueService = new QueueService();
        String paramForPostMethod = "temperature=18";
        /* ��������� ������ � ������� weather. ����� queue */
        queueService.process(
                new Req("POST", "queue", "weather", paramForPostMethod)
        );
        /* �������� ������ �� ������� weather. ����� queue */
        Resp result = queueService.process(
                new Req("GET", "queue", "weather", null)
        );
        assertThat(result.text(), is("temperature=18"));
    }

}