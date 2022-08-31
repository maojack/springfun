# SpringBoot Test Application
This project attempts to mimic a subscriber-publisher server.

### Development Environment:
* Windows 11
* Java 11
* Maven(Windows) 4.0.0
* SpringBoot 2.7.3

### Server Requirements:
Creating a new subscription for all events under `/subscribe/{TOPIC}` and forward its data to `localhost:8000/event`:
<pre>
<code>POST /subscribe/{TOPIC}
BODY { url: "http://localhost:8000/event"}
</code></pre>

Then, publishing new data to the subscriber under `/subscribe/{TOPIC}`:
<pre><code>POST /publish/{TOPIC}
BODY { "message": "hello"}
</code></pre>

Running the project and attempt to publish an event:
<pre>
<code>$ ./start-server.sh </code>
<code>$ curl -X POST -H "Content-Type: application/json" -d '{"url": "http://localhost:8000/event"}' http://localhost:8000/subscribe/topic1</code>
<code>$ curl -X POST -H "Content-Type: application/json" -d '{"message": "hello"}' http://localhost:8000/publish/topic1</code>
</pre>     
The above code would set up a subscription between topic1 and `http://localhost:8000/event`.
When the event is published in line 3, it would send both the topic and body as JSON to `http://localhost:8000`

The `/event` endpoint is just used to print the data and verify everything is working.

![alt text](/images/pubsub-diagram.png)
