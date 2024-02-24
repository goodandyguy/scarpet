__command()->(
    e = query(player(),'trace',32,'entities');
    dscr = [
        '*',
        'valid',
        'living',
        'projectile',
        'minecarts',
        'undead',
        'arthropod',
        'aquatic',
        'regular',
        'illager'
    ];
    if(e!=null,(
        etags = query(e,'entity_tags');
        ecats = query(e,'category');
        etype = query(e,'type');
        put(etags,null,ecats,'extend');
        etags+=etype;
        for(dscr,if(entity_types(_)~etype!=null,etags+=_));
        if(etags:0==null,print(format('rb The entity has no tags.')));
        fstr = 'tb Data of the entity-';
        for(etags,if(length(fstr)>21,fstr+='- \n-');fstr+='-wb '+_+'-^wi Click to copy'+'-&'+_);
        print(format(split('-',fstr)));
    ),
        print(format('rb You aren\'t looking at an entity!'));
    );
    return();
);
