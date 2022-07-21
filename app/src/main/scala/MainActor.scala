import UserActor.{AkkaCommand}
import akka.actor.typed.Behavior
import akka.actor.typed.pubsub.Topic
import akka.actor.typed.scaladsl.Behaviors

object MainActor {

  def apply(chatControllerImpl: ChatControllerImpl): Behavior[AkkaCommand] = chatting(chatControllerImpl)

  private def chatting(chatControllerImpl: ChatControllerImpl): Behavior[AkkaCommand] = {
    Behaviors.setup { context =>
      val actor = context.spawn(UserActor(chatControllerImpl), "my-actor")
      val topic = context.spawn(Topic[AkkaCommand]("my-topic"), "my-topic")

      topic ! Topic.Subscribe(actor)

      Behaviors.receiveMessage {
        Message: AkkaCommand =>
          topic ! Topic.Publish(Message)
          Behaviors.same
      }
    }
  }
}