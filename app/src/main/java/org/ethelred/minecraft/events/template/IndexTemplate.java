package org.ethelred.minecraft.events.template;

import io.jstach.jstache.JStache;
import io.jstach.jstache.JStacheLambda;
import org.ethelred.minecraft.events.model.Player;
import org.ethelred.minecraft.events.model.World;

import java.util.SortedSet;

@JStache(template =
// language=mustache
        """
<html>
<head>
<title>{{title}}</title>
</head>
<body>
<h1>{{title}}</h1>
{{^worlds}}
<p>No location data.</p>
{{/worlds}}
{{#worlds}}
<h2>World: {{name}}</h2>
<ul>
{{#players}}
<li><strong>{{name}}</strong> - {{lastLocation.dimension}} {{#coords}}{{/coords}}</li>
{{/players}}
</ul>
{{/worlds}}
</body>
</html>
""")
public record IndexTemplate(String title, SortedSet<World> worlds) {
    @JStacheLambda
    @JStacheLambda.Raw
    public String coords(Player player) {
        var v = player.lastLocation().coordinates();
        return "%d, %d, %d".formatted(
                (int) v.x(),
                (int) v.y(),
                (int) v.z()
        );
    }
}
