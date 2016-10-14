package com.github.bsamartins.spring.graphql.schema;

import com.github.bsamartins.spring.graphql.model.Person;
import com.github.bsamartins.spring.graphql.schema.objecttype.RootObjectType;
import com.oembedler.moon.graphql.engine.stereotype.*;

/**
 * Created by martinsb on 13/10/2016.
 */
@GraphQLSchema
public class AppSchema {

    @GraphQLSchemaQuery
    private RootObjectType root;

    @GraphQLMutation
    @GraphQLDescription("Mutation to add new todo item")
    @GraphQLOut("result")
    public String increment(
            @GraphQLIn("prefix") String prefix,
            @GraphQLIn("text") @GraphQLNonNull String text) {
        return prefix + text;
    }

}
