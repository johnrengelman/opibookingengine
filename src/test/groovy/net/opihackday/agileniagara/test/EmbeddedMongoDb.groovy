package net.opihackday.agileniagara.test

import static de.flapdoodle.embed.mongo.distribution.Version.Main.PRODUCTION

import de.flapdoodle.embed.mongo.MongodExecutable
import de.flapdoodle.embed.mongo.MongodStarter
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder
import de.flapdoodle.embed.mongo.config.Net
import de.flapdoodle.embed.process.runtime.Network
import org.springframework.stereotype.Component

import javax.annotation.PostConstruct
import javax.annotation.PreDestroy

@Component
class EmbeddedMongoDB {

    int port = 7777

    private MongodExecutable executable

    public EmbeddedMongoDB() {
        def config = new MongodConfigBuilder().with {
            version PRODUCTION
            net new Net(port, Network.localhostIsIPv6())
            build()
        }
        executable = MongodStarter.defaultInstance.prepare(config)
    }

    @PostConstruct
    void start() {
        executable.start();
    }

    @PreDestroy
    void stop() {
        executable.stop();
    }

}
