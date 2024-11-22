package com.lxk.druid;

import org.apache.shardingsphere.sql.parser.api.CacheOption;
import org.apache.shardingsphere.sql.parser.api.SQLParserEngine;
import org.apache.shardingsphere.sql.parser.api.SQLVisitorEngine;
import org.apache.shardingsphere.sql.parser.api.parser.SQLParser;
import org.apache.shardingsphere.sql.parser.api.visitor.ASTNode;
import org.apache.shardingsphere.sql.parser.core.ParseASTNode;
import org.apache.shardingsphere.sql.parser.core.SQLParserFactory;
import org.apache.shardingsphere.sql.parser.mysql.parser.MySQLLexer;
import org.apache.shardingsphere.sql.parser.mysql.parser.MySQLParser;
import org.apache.shardingsphere.sql.parser.sql.dialect.statement.mysql.MySQLStatement;
import org.junit.Before;
import org.junit.Test;

import java.util.Properties;

/**
 * @author LiXuekai on 2024/11/22
 */
public class TestShardingSphere {
    private String sql;

    @Before
    public void init() {
        //sql = "SELECT column_name FROM table1 WHERE column_name = (SELECT column_name2 FROM table2 WHERE condition);";
        //sql = "SELECT a.column_name1, b.column_name2 FROM (SELECT column_name1 FROM table1 WHERE condition1) AS a JOIN (SELECT column_name2 FROM table2 WHERE condition2) AS b ON a.column_name1 = b.column_name2;";
        //sql = "SELECT (SELECT column_name FROM table2 WHERE table2.column_name = table1.column_name) AS column_name_alias FROM table1;";
        //sql = "select * from (select * from temp) a";
        //sql = "SELECT id FROM user WHERE status = 1;";
        //sql = "SELECT id FROM user WHERE status = 1;SELECT id FROM user WHERE status = 2;SELECT id FROM user WHERE status = 3;";
        sql = "select `dcbsdb`.`dpctla`.`QUOT_TYP_2` AS `QUOT_TYP_2`,`dcbsdb`.`dpctla`.`CRNT_CYCL_ACML_VLU_35` AS `CRNT_CYCL_ACML_VLU_35`,`dcbsdb`.`dpctla`.`QUOT_TYP_3` AS `QUOT_TYP_3`,`dcbsdb`.`dpctla`.`CRNT_CYCL_ACML_VLU_36` AS `CRNT_CYCL_ACML_VLU_36`,`dcbsdb`.`dpctla`.`QUOT_TYP_4` AS `QUOT_TYP_4`,`dcbsdb`.`dpctla`.`CRNT_CYCL_ACML_VLU_33` AS `CRNT_CYCL_ACML_VLU_33`,`dcbsdb`.`dpctla`.`QUOT_TYP_5` AS `QUOT_TYP_5`,`dcbsdb`.`dpctla`.`CRNT_CYCL_ACML_VLU_34` AS `CRNT_CYCL_ACML_VLU_34`,`dcbsdb`.`dpctla`.`CRNT_CYCL_ACML_VLU_31` AS `CRNT_CYCL_ACML_VLU_31`,`dcbsdb`.`dpctla`.`CRNT_CYCL_ACML_VLU_32` AS `CRNT_CYCL_ACML_VLU_32`,`dcbsdb`.`dpctla`.`QUOT_TYP_1` AS `QUOT_TYP_1`,`dcbsdb`.`dpctla`.`CRNT_CYCL_ACML_VLU_30` AS `CRNT_CYCL_ACML_VLU_30`,`dcbsdb`.`dpctla`.`QUOT_TYP_6` AS `QUOT_TYP_6`,`dcbsdb`.`dpctla`.`QUOT_TYP_7` AS `QUOT_TYP_7`,`dcbsdb`.`dpctla`.`QUOT_TYP_8` AS `QUOT_TYP_8`,`dcbsdb`.`dpctla`.`QUOT_TYP_9` AS `QUOT_TYP_9`,`dcbsdb`.`dpctla`.`CRNT_CYCL_ACML_VLU_39` AS `CRNT_CYCL_ACML_VLU_39`,`dcbsdb`.`dpctla`.`CRNT_CYCL_ACML_VLU_37` AS `CRNT_CYCL_ACML_VLU_37`,`dcbsdb`.`dpctla`.`CRNT_CYCL_ACML_VLU_38` AS `CRNT_CYCL_ACML_VLU_38`,`dcbsdb`.`dpctla`.`CRNT_CYCL_ACML_VLU_24` AS `CRNT_CYCL_ACML_VLU_24`,`dcbsdb`.`dpctla`.`CRNT_CYCL_ACML_VLU_25` AS `CRNT_CYCL_ACML_VLU_25`,`dcbsdb`.`dpctla`.`CRNT_CYCL_ACML_VLU_9` AS `CRNT_CYCL_ACML_VLU_9`,`dcbsdb`.`dpctla`.`CRNT_CYCL_ACML_VLU_22` AS `CRNT_CYCL_ACML_VLU_22`,`dcbsdb`.`dpctla`.`CRNT_CYCL_ACML_VLU_8` AS `CRNT_CYCL_ACML_VLU_8`,`dcbsdb`.`dpctla`.`CRNT_CYCL_ACML_VLU_23` AS `CRNT_CYCL_ACML_VLU_23`,`dcbsdb`.`dpctla`.`CRNT_CYCL_ACML_VLU_7` AS `CRNT_CYCL_ACML_VLU_7`,`dcbsdb`.`dpctla`.`CRNT_CYCL_ACML_VLU_20` AS `CRNT_CYCL_ACML_VLU_20`,`dcbsdb`.`dpctla`.`CRNT_CYCL_ACML_VLU_6` AS `CRNT_CYCL_ACML_VLU_6`,`dcbsdb`.`dpctla`.`CRNT_CYCL_ACML_VLU_21` AS `CRNT_CYCL_ACML_VLU_21`,`dcbsdb`.`dpctla`.`CRNT_CYCL_ACML_VLU_5` AS `CRNT_CYCL_ACML_VLU_5`,`dcbsdb`.`dpctla`.`CRNT_CYCL_ACML_VLU_4` AS `CRNT_CYCL_ACML_VLU_4`,`dcbsdb`.`dpctla`.`CRNT_CYCL_ACML_VLU_3` AS `CRNT_CYCL_ACML_VLU_3`,`dcbsdb`.`dpctla`.`QUOT_TYP_17` AS `QUOT_TYP_17`,`dcbsdb`.`dpctla`.`CRNT_CYCL_ACML_VLU_2` AS `CRNT_CYCL_ACML_VLU_2`,`dcbsdb`.`dpctla`.`QUOT_TYP_18` AS `QUOT_TYP_18`,`dcbsdb`.`dpctla`.`CRNT_CYCL_ACML_VLU_1` AS `CRNT_CYCL_ACML_VLU_1`,`dcbsdb`.`dpctla`.`QUOT_TYP_19` AS `QUOT_TYP_19`,`dcbsdb`.`dpctla`.`QUOT_TYP_13` AS `QUOT_TYP_13`,`dcbsdb`.`dpctla`.`QUOT_TYP_14` AS `QUOT_TYP_14`,`dcbsdb`.`dpctla`.`QUOT_TYP_15` AS `QUOT_TYP_15`,`dcbsdb`.`dpctla`.`QUOT_TYP_16` AS `QUOT_TYP_16`,`dcbsdb`.`dpctla`.`QUOT_TYP_10` AS `QUOT_TYP_10`,`dcbsdb`.`dpctla`.`QUOT_TYP_11` AS `QUOT_TYP_11`,`dcbsdb`.`dpctla`.`QUOT_TYP_12` AS `QUOT_TYP_12`,`dcbsdb`.`dpctla`.`LAST_PERD_ACML_VLU_18` AS `LAST_PERD_ACML_VLU_18`,`dcbsdb`.`dpctla`.`LAST_PERD_ACML_VLU_19` AS `LAST_PERD_ACML_VLU_19`,`dcbsdb`.`dpctla`.`LAST_PERD_ACML_VLU_16` AS `LAST_PERD_ACML_VLU_16`,`dcbsdb`.`dpctla`.`LAST_PERD_ACML_VLU_17` AS `LAST_PERD_ACML_VLU_17`,`dcbsdb`.`dpctla`.`LAST_PERD_ACML_VLU_14` AS `LAST_PERD_ACML_VLU_14`,`dcbsdb`.`dpctla`.`LAST_PERD_ACML_VLU_15` AS `LAST_PERD_ACML_VLU_15`,`dcbsdb`.`dpctla`.`LAST_PERD_ACML_VLU_12` AS `LAST_PERD_ACML_VLU_12`,`dcbsdb`.`dpctla`.`LAST_PERD_ACML_VLU_13` AS `LAST_PERD_ACML_VLU_13`,`dcbsdb`.`dpctla`.`LAST_PERD_ACML_VLU_10` AS `LAST_PERD_ACML_VLU_10`,`dcbsdb`.`dpctla`.`CRNT_CYCL_ACML_VLU_28` AS `CRNT_CYCL_ACML_VLU_28`,`dcbsdb`.`dpctla`.`LAST_PERD_ACML_VLU_11` AS `LAST_PERD_ACML_VLU_11`,`dcbsdb`.`dpctla`.`CRNT_CYCL_ACML_VLU_29` AS `CRNT_CYCL_ACML_VLU_29`,`dcbsdb`.`dpctla`.`CRNT_CYCL_ACML_VLU_26` AS `CRNT_CYCL_ACML_VLU_26`,`dcbsdb`.`dpctla`.`CRNT_CYCL_ACML_VLU_27` AS `CRNT_CYCL_ACML_VLU_27`,`dcbsdb`.`dpctla`.`CRNT_CYCL_ACML_VLU_13` AS `CRNT_CYCL_ACML_VLU_13`,`dcbsdb`.`dpctla`.`CRNT_CYCL_ACML_VLU_14` AS `CRNT_CYCL_ACML_VLU_14`,`dcbsdb`.`dpctla`.`CRNT_CYCL_ACML_VLU_11` AS `CRNT_CYCL_ACML_VLU_11`,`dcbsdb`.`dpctla`.`CRNT_CYCL_ACML_VLU_12` AS `CRNT_CYCL_ACML_VLU_12`,`dcbsdb`.`dpctla`.`CRNT_CYCL_ACML_VLU_10` AS `CRNT_CYCL_ACML_VLU_10`,`dcbsdb`.`dpctla`.`QUOT_TYP_28` AS `QUOT_TYP_28`,`dcbsdb`.`dpctla`.`QUOT_TYP_29` AS `QUOT_TYP_29`,`dcbsdb`.`dpctla`.`LAST_UPDT_DATE_40` AS `LAST_UPDT_DATE_40`,`dcbsdb`.`dpctla`.`QUOT_TYP_24` AS `QUOT_TYP_24`,`dcbsdb`.`dpctla`.`QUOT_TYP_25` AS `QUOT_TYP_25`,`dcbsdb`.`dpctla`.`QUOT_TYP_26` AS `QUOT_TYP_26`,`dcbsdb`.`dpctla`.`QUOT_TYP_27` AS `QUOT_TYP_27`,`dcbsdb`.`dpctla`.`QUOT_TYP_20` AS `QUOT_TYP_20`,`dcbsdb`.`dpctla`.`LOCL_TMTP` AS `LOCL_TMTP`,`dcbsdb`.`dpctla`.`QUOT_TYP_21` AS `QUOT_TYP_21`,`dcbsdb`.`dpctla`.`QUOT_TYP_22` AS `QUOT_TYP_22`,`dcbsdb`.`dpctla`.`QUOT_TYP_23` AS `QUOT_TYP_23`,`dcbsdb`.`dpctla`.`LAST_PERD_ACML_VLU_29` AS `LAST_PERD_ACML_VLU_29`,`dcbsdb`.`dpctla`.`DTBT_KEY` AS `DTBT_KEY`,`dcbsdb`.`dpctla`.`LAST_PERD_ACML_VLU_27` AS `LAST_PERD_ACML_VLU_27`,`dcbsdb`.`dpctla`.`LAST_PERD_ACML_VLU_28` AS `LAST_PERD_ACML_VLU_28`,`dcbsdb`.`dpctla`.`LAST_PERD_ACML_VLU_25` AS `LAST_PERD_ACML_VLU_25`,`dcbsdb`.`dpctla`.`LAST_PERD_ACML_VLU_26` AS `LAST_PERD_ACML_VLU_26`,`dcbsdb`.`dpctla`.`CRNT_CYCL_ACML_VLU_19` AS `CRNT_CYCL_ACML_VLU_19`,`dcbsdb`.`dpctla`.`LAST_PERD_ACML_VLU_23` AS `LAST_PERD_ACML_VLU_23`,`dcbsdb`.`dpctla`.`LAST_PERD_ACML_VLU_24` AS `LAST_PERD_ACML_VLU_24`,`dcbsdb`.`dpctla`.`CRNT_CYCL_ACML_VLU_17` AS `CRNT_CYCL_ACML_VLU_17`,`dcbsdb`.`dpctla`.`LAST_PERD_ACML_VLU_21` AS `LAST_PERD_ACML_VLU_21`,`dcbsdb`.`dpctla`.`CRNT_CYCL_ACML_VLU_18` AS `CRNT_CYCL_ACML_VLU_18`,`dcbsdb`.`dpctla`.`LAST_PERD_ACML_VLU_22` AS `LAST_PERD_ACML_VLU_22`,`dcbsdb`.`dpctla`.`CRNT_CYCL_ACML_VLU_15` AS `CRNT_CYCL_ACML_VLU_15`,`dcbsdb`.`dpctla`.`CRNT_CYCL_ACML_VLU_16` AS `CRNT_CYCL_ACML_VLU_16`,`dcbsdb`.`dpctla`.`LAST_PERD_ACML_VLU_20` AS `LAST_PERD_ACML_VLU_20`,`dcbsdb`.`dpctla`.`LAST_UPDT_DATE_35` AS `LAST_UPDT_DATE_35`,`dcbsdb`.`dpctla`.`LAST_UPDT_DATE_36` AS `LAST_UPDT_DATE_36`,`dcbsdb`.`dpctla`.`LAST_UPDT_DATE_37` AS `LAST_UPDT_DATE_37`,`dcbsdb`.`dpctla`.`LAST_UPDT_DATE_38` AS `LAST_UPDT_DATE_38`,`dcbsdb`.`dpctla`.`LAST_UPDT_DATE_31` AS `LAST_UPDT_DATE_31`,`dcbsdb`.`dpctla`.`LAST_UPDT_DATE_32` AS `LAST_UPDT_DATE_32`,`dcbsdb`.`dpctla`.`LAST_UPDT_DATE_33` AS `LAST_UPDT_DATE_33`,`dcbsdb`.`dpctla`.`LAST_UPDT_DATE_34` AS `LAST_UPDT_DATE_34`,`dcbsdb`.`dpctla`.`QUOT_TYP_39` AS `QUOT_TYP_39`,`dcbsdb`.`dpctla`.`LAST_UPDT_DATE_30` AS `LAST_UPDT_DATE_30`,`dcbsdb`.`dpctla`.`QUOT_TYP_35` AS `QUOT_TYP_35`,`dcbsdb`.`dpctla`.`RCRD_STAS` AS `RCRD_STAS`,`dcbsdb`.`dpctla`.`QUOT_TYP_36` AS `QUOT_TYP_36`,`dcbsdb`.`dpctla`.`QUOT_TYP_37` AS `QUOT_TYP_37`,`dcbsdb`.`dpctla`.`QUOT_TYP_38` AS `QUOT_TYP_38`,`dcbsdb`.`dpctla`.`QUOT_TYP_31` AS `QUOT_TYP_31`,`dcbsdb`.`dpctla`.`LAST_UPDT_DATE_7` AS `LAST_UPDT_DATE_7`,`dcbsdb`.`dpctla`.`QUOT_TYP_32` AS `QUOT_TYP_32`,`dcbsdb`.`dpctla`.`LAST_UPDT_DATE_6` AS `LAST_UPDT_DATE_6`,`dcbsdb`.`dpctla`.`QUOT_TYP_33` AS `QUOT_TYP_33`,`dcbsdb`.`dpctla`.`LAST_UPDT_DATE_9` AS `LAST_UPDT_DATE_9`,`dcbsdb`.`dpctla`.`QUOT_TYP_34` AS `QUOT_TYP_34`,`dcbsdb`.`dpctla`.`LAST_UPDT_DATE_8` AS `LAST_UPDT_DATE_8`,`dcbsdb`.`dpctla`.`LAST_UPDT_DATE_3` AS `LAST_UPDT_DATE_3`,`dcbsdb`.`dpctla`.`LAST_UPDT_DATE_2` AS `LAST_UPDT_DATE_2`,`dcbsdb`.`dpctla`.`LAST_PERD_ACML_VLU_38` AS `LAST_PERD_ACML_VLU_38`,`dcbsdb`.`dpctla`.`LAST_UPDT_DATE_5` AS `LAST_UPDT_DATE_5`,`dcbsdb`.`dpctla`.`QUOT_TYP_30` AS `QUOT_TYP_30`,`dcbsdb`.`dpctla`.`LAST_PERD_ACML_VLU_39` AS `LAST_PERD_ACML_VLU_39`,`dcbsdb`.`dpctla`.`LAST_UPDT_DATE_4` AS `LAST_UPDT_DATE_4`,`dcbsdb`.`dpctla`.`LAST_PERD_ACML_VLU_36` AS `LAST_PERD_ACML_VLU_36`,`dcbsdb`.`dpctla`.`LAST_PERD_ACML_VLU_37` AS `LAST_PERD_ACML_VLU_37`,`dcbsdb`.`dpctla`.`LAST_PERD_ACML_VLU_34` AS `LAST_PERD_ACML_VLU_34`,`dcbsdb`.`dpctla`.`LAST_UPDT_DATE_1` AS `LAST_UPDT_DATE_1`,`dcbsdb`.`dpctla`.`LAST_PERD_ACML_VLU_35` AS `LAST_PERD_ACML_VLU_35`,`dcbsdb`.`dpctla`.`LAST_UPDT_DATE_39` AS `LAST_UPDT_DATE_39`,`dcbsdb`.`dpctla`.`LAST_PERD_ACML_VLU_32` AS `LAST_PERD_ACML_VLU_32`,`dcbsdb`.`dpctla`.`LAST_PERD_ACML_VLU_33` AS `LAST_PERD_ACML_VLU_33`,`dcbsdb`.`dpctla`.`LAST_PERD_ACML_VLU_30` AS `LAST_PERD_ACML_VLU_30`,`dcbsdb`.`dpctla`.`LAST_PERD_ACML_VLU_31` AS `LAST_PERD_ACML_VLU_31`,`dcbsdb`.`dpctla`.`LAST_UPDT_DATE_24` AS `LAST_UPDT_DATE_24`,`dcbsdb`.`dpctla`.`LAST_PERD_ACML_VLU_40` AS `LAST_PERD_ACML_VLU_40`,`dcbsdb`.`dpctla`.`LAST_UPDT_DATE_25` AS `LAST_UPDT_DATE_25`,`dcbsdb`.`dpctla`.`LAST_UPDT_DATE_26` AS `LAST_UPDT_DATE_26`,`dcbsdb`.`dpctla`.`LAST_UPDT_DATE_27` AS `LAST_UPDT_DATE_27`,`dcbsdb`.`dpctla`.`LAST_UPDT_DATE_20` AS `LAST_UPDT_DATE_20`,`dcbsdb`.`dpctla`.`LAST_PERD_ACML_VLU_1` AS `LAST_PERD_ACML_VLU_1`,`dcbsdb`.`dpctla`.`LAST_UPDT_DATE_21` AS `LAST_UPDT_DATE_21`,`dcbsdb`.`dpctla`.`LAST_PERD_ACML_VLU_2` AS `LAST_PERD_ACML_VLU_2`,`dcbsdb`.`dpctla`.`LAST_UPDT_DATE_22` AS `LAST_UPDT_DATE_22`,`dcbsdb`.`dpctla`.`LAST_UPDT_DATE_23` AS `LAST_UPDT_DATE_23`,`dcbsdb`.`dpctla`.`QUOT_TYP_40` AS `QUOT_TYP_40`,`dcbsdb`.`dpctla`.`LAST_UPDT_DATE_28` AS `LAST_UPDT_DATE_28`,`dcbsdb`.`dpctla`.`LAST_UPDT_DATE_29` AS `LAST_UPDT_DATE_29`,`dcbsdb`.`dpctla`.`LAST_UPDT_DATE_13` AS `LAST_UPDT_DATE_13`,`dcbsdb`.`dpctla`.`LAST_UPDT_DATE_14` AS `LAST_UPDT_DATE_14`,`dcbsdb`.`dpctla`.`TMTP_TIME` AS `TMTP_TIME`,`dcbsdb`.`dpctla`.`LAST_UPDT_DATE_15` AS `LAST_UPDT_DATE_15`,`dcbsdb`.`dpctla`.`LAST_UPDT_DATE_16` AS `LAST_UPDT_DATE_16`,`dcbsdb`.`dpctla`.`LAST_UPDT_DATE_10` AS `LAST_UPDT_DATE_10`,`dcbsdb`.`dpctla`.`LAST_UPDT_DATE_11` AS `LAST_UPDT_DATE_11`,`dcbsdb`.`dpctla`.`LAST_UPDT_DATE_12` AS `LAST_UPDT_DATE_12`,`dcbsdb`.`dpctla`.`LOCL_TRAN_TIME` AS `LOCL_TRAN_TIME`,`dcbsdb`.`dpctla`.`LAST_UPDT_DATE_17` AS `LAST_UPDT_DATE_17`,`dcbsdb`.`dpctla`.`LAST_UPDT_DATE_18` AS `LAST_UPDT_DATE_18`,`dcbsdb`.`dpctla`.`LAST_UPDT_DATE_19` AS `LAST_UPDT_DATE_19`,`dcbsdb`.`dpctla`.`CTRL_OBJT_TYP` AS `CTRL_OBJT_TYP`,`dcbsdb`.`dpctla`.`LOCL_TRAN_DATE` AS `LOCL_TRAN_DATE`,`dcbsdb`.`dpctla`.`CTRL_OBJT_NUM` AS `CTRL_OBJT_NUM`,`dcbsdb`.`dpctla`.`CRNT_CYCL_ACML_VLU_40` AS `CRNT_CYCL_ACML_VLU_40`,`dcbsdb`.`dpctla`.`BANK_NUM` AS `BANK_NUM`,`dcbsdb`.`dpctla`.`LAST_PERD_ACML_VLU_5` AS `LAST_PERD_ACML_VLU_5`,`dcbsdb`.`dpctla`.`LAST_PERD_ACML_VLU_6` AS `LAST_PERD_ACML_VLU_6`,`dcbsdb`.`dpctla`.`LAST_PERD_ACML_VLU_3` AS `LAST_PERD_ACML_VLU_3`,`dcbsdb`.`dpctla`.`LAST_PERD_ACML_VLU_4` AS `LAST_PERD_ACML_VLU_4`,`dcbsdb`.`dpctla`.`LAST_PERD_ACML_VLU_9` AS `LAST_PERD_ACML_VLU_9`,`dcbsdb`.`dpctla`.`LAST_PERD_ACML_VLU_7` AS `LAST_PERD_ACML_VLU_7`,`dcbsdb`.`dpctla`.`LAST_PERD_ACML_VLU_8` AS `LAST_PERD_ACML_VLU_8` from `dcbsdb`.`dpctla` where ((`dpctla`.`BANK_NUM` = '001') and (`dpctla`.`CTRL_OBJT_NUM` = '6217711107694984') and (`dpctla`.`CTRL_OBJT_TYP` = '3') and (`dpctla`.`DTBT_KEY` = '6217711107694984')) order by `dpctla`.`BANK_NUM`,`dpctla`.`CTRL_OBJT_NUM`,`dpctla`.`CTRL_OBJT_TYP` limit 1";
    }

    @Test
    public void parse() {
        CacheOption cacheOption = new CacheOption(128, 1024L);
        SQLParserEngine parserEngine = new SQLParserEngine("MySQL", cacheOption);
        int sum = 100000;
        long a = System.currentTimeMillis();
        while (sum > 0) {
            ParseASTNode parse = parserEngine.parse(sql, false);
            sum--;
        }
        System.out.println("parse 长1句 执行耗时 : " + (System.currentTimeMillis() - a) / 1000f + " 秒 ");
    }

    @Test
    public void a() {
        CacheOption cacheOption = new CacheOption(128, 1024L);
        SQLParserEngine parserEngine = new SQLParserEngine("MySQL", cacheOption);
        ParseASTNode parse = parserEngine.parse(sql, false);


        System.out.println(parse.hashCode());

        SQLVisitorEngine sqlVisitorEngine = new SQLVisitorEngine("MySQL", "STATEMENT", true, new Properties());
        Object visit = sqlVisitorEngine.visit(parse);
        System.out.println(visit);
    }

    @Test
    public void parse2() {
        SQLParser sqlParser = SQLParserFactory.newInstance(sql, MySQLLexer.class, MySQLParser.class);
        ASTNode parse = sqlParser.parse();
        SQLVisitorEngine sqlVisitorEngine = new SQLVisitorEngine("MySQL", "STATEMENT", true, new Properties());

        MySQLStatement visit = (MySQLStatement) sqlVisitorEngine.visit((ParseASTNode) parse);
        System.out.println(visit);
    }
}
