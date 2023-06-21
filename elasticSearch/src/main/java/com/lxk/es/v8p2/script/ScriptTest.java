package com.lxk.es.v8p2.script;

import co.elastic.clients.elasticsearch._types.Script;
import com.lxk.es.v8p2.base.Common;
import org.junit.Test;

/**
 * @author LiXuekai on 2023/6/21
 */
public class ScriptTest extends Common {

    @Test
    public void script() {
        Script.Builder builder = new Script.Builder();
        //builder.inline(v->v.lang())
        //Script script = new Script("Math.random()");

    }
}
