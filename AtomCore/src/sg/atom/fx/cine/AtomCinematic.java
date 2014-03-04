/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.fx.cine;

import com.google.common.util.concurrent.ExecutionList;

/**
 * Extension for JME3's Cinematic system. which made by bunch of events, mean it
 * just manage the start and duration of the tracks; AtomCinematic manages start,
 * duration, transistion and much more, in fact it manage everythings that run
 * in a specific time in the timeline!
 *
 * <p>Unique features: Multiple timelines, customizable routines; suitable for
 * games with complex in-game cinematic.</p>
 *
 * Details: <ul>
 *
 * <li>Events or tracks, runable can be added/injected into the Cinematic
 * timelines.</li>
 *
 * <li>Events can be broadcast to outside via EventBus</li>
 *
 * <li>The routines can be interupt change programaticly or scripted. Enable
 * mid-term realtime interaction. (like physic based.. etc)</li>
 *
 * <li>Scripted cinematic system are very flexible: it can take complex
 * dialogues (sounds, subtitles), character emotions (facial, animations...) and
 * cinema-photographic details (camera angles, music, moods, enviroments..) into
 * accounts.</li>
 *
 * <li>Open hooks to intergrate with other system and espcially tools to create
 * contents.</li> </ul>
 *
 * @author CuongNguyen
 */
public class AtomCinematic {

    ExecutionList executionList;
}
