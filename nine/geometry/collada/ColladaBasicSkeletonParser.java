package nine.geometry.collada;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import nine.function.RefreshStatus;

public class ColladaBasicSkeletonParser implements ColladaSkeletonParser
{
    @Override
    public void read(ColladaNode node, Animator animator, RefreshStatus refresh, SkeletonReader reader)
    {
        node.children("COLLADA", root ->
        root.children("library_visual_scenes", scenes ->
        scenes.children("visual_scene", scene ->
        {
            List<ColladaNode> controllers = new ArrayList<>();
            HashMap<String, Animation> bones = new HashMap<>();
            scene.children("node", new ColladaBoneNodeReader(Animation.none, animator, refresh, bones::put, controllers::add));
            for(ColladaNode controller : controllers)
            {
                controller.attribute("url", skinId ->
                {
                    reader.read(skinId, new MapSkeleton(bones));
                });
            }
        })));
    }
}