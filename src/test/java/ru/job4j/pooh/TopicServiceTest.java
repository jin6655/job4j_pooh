package ru.job4j.pooh;

import org.junit.Test;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class TopicServiceTest {

    @Test
    public void whenTopic() {
        TopicService topicService = new TopicService();
        String paramForPublisher = "temperature=18";
        String paramForSubscriber1 = "client407";
        String paramForSubscriber2 = "client6565";
        /* ����� topic. ������������� �� ����� weather. client407. */
        topicService.process(
                new Req("GET", "topic", "weather", paramForSubscriber1)
        );
        /* ����� topic. ��������� ������ � ����� weather. */
        topicService.process(
                new Req("POST", "topic", "weather", paramForPublisher)
        );
        /* ����� topic. �������� ������ �� �������������� ������� � ������ weather. ������� client407. */
        Resp result1 = topicService.process(
                new Req("GET", "topic", "weather", paramForSubscriber1)
        );
 /* ����� topic. �������� ������ �� �������������� ������� � ������ weather. ������� client6565.
 ������� �����������, �.�. ��� �� ��� �������� - ������� ������ ������ */
        Resp result2 = topicService.process(
                new Req("GET", "topic", "weather", paramForSubscriber2)
        );
        assertThat(result1.text(), is("temperature=18"));
        assertThat(result2.text(), is(""));
    }

}