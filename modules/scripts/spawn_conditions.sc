__config() -> {
    'scope' -> 'global',
    'resources' -> [
        {
            'source' -> '/configs/spawn_conditions.json',
            'target' -> 'spawn_conditions.json',
            'shared' -> true,
        },
        {
            'source' -> '/README/spawn_conditions.md',
            'target' -> 'README/spawn_conditions.md',
            'shared' -> true,
        },
    ],
};

config_data = read_file('spawn_conditions','shared_json');
spec_cond = {
    'dimension'->_(_e,val)->(
        return(val~query(_e,'dimension')!=null);
    ),
    'day_time'->_(_e,val)->(
        mint = val:0;
        maxt = val:1;
        dt = day_time();
        if(maxt==null,maxt=24001);
        return(dt<=maxt&&dt>mint);
    ),
    'x'->_(_e,val)->(
        minn = val:0;
        maxn = val:1;
        epos = query(_e,'pos'):0;
        if(maxn==null,maxn=10^1000);
        return(epos<=maxn&&epos>minn);
    ),
    'y'->_(_e,val)->(
        minn = val:0;
        maxn = val:1;
        epos = query(_e,'pos'):1;
        if(maxn==null,maxn=10^1000);
        return(epos<=maxn&&epos>minn);
    ),
    'z'->_(_e,val)->(
        minn = val:0;
        maxn = val:1;
        epos = query(_e,'pos'):2;
        if(maxn==null,maxn=10^1000);
        return(epos<=maxn&&epos>minn);
    ),
    'area'->_(_e,val)->(
        minn = val:0;
        maxn = val:1;
        big = 10^1000;
        epos = query(_e,'pos');
        if(maxn==null,maxn=[big,big,big]);
        return(epos:0<=maxn:0&&epos:0>minn:0&&epos:1<=maxn:1&&epos:1>minn:1&&epos:2<=maxn:2&&epos:2>minn:2);
    ),
    'spawn'->_(_e,val)->(
        return(true);
    ),
};

entity_load_handler('*',_(e,new,outer(config_data),outer(spec_cond))->
    entity_event(e, 'on_tick', _(e,outer(config_data),outer(spec_cond)) ->
        (
            spl = split(':',query(e,'type'));
            namespace = spl:0;
            e_type = spl:1;
            e_name = namespace+':'+e_type;
            if(namespace==e_type,namespace='minecraft';e_name=e_type);
            ns = config_data:namespace; // Get the entity's namespace in filter list
            gtags = config_data:'#'; // Get all global tags O:1
            nstags = map(filter(ns,slice(_,0,1)=='#'),slice(_,1)); // Get all namespace specific tags O:2
            gtgd = null; // Value of first global tag that applied
            nstgd = null; // Value of first namespace tag that applied
            for(gtags,(
                if(entity_types(_)~e_name!=null,gtgd=gtags:_);
                if(gtgd!=null,break());)
            );
            for(nstags,(
                if(entity_types(_)~e_name!=null,nstgd=ns:'#'+_);
                if(nstgd!=null,break());)
            );
            nsdat = ns:e_type; // Value of entity in it's namespace
            nsspt = true; // Temporary namespace tag handler
            nssbot = nstgd; // Namespace spawn based on tag
            espt = true; // Temporary entity tag handler
            esbot = nsdat; // Entity spawn based on tag
            gspt = true; // Global spawn tag / handler
            gsbot = gtgd; // Global Spawn based on tags, true when all tags apply
            if(type(gtgd)=='map',
                // If the gtgd is a map, it contains conditions under which to spawn/not spawn the entity
                gsbot = gtgd:'spawn';
                for(gtgd,
                    (
                        gspt = call(spec_cond:_,e,gtgd:_);
                        if(gspt==false,gsbot= !gsbot;break());
                    );
                );
            );
            if(type(nstgd)=='map',
                // Same as global, just for namespace
                nssbot = nstgd:'spawn';
                for(nstgd,
                    (
                        nsspt = call(spec_cond:_,e,nstgd:_);
                        if(nsspt==false,nssbot= !nssbot;break());
                    );
                );
            );
            if(type(nsdat)=='map',
                // Same as global, just for entity
                esbot = nsdat:'spawn';
                for(nsdat,
                    (
                        espt = call(spec_cond:_,e,nsdat:_);
                        if(espt==false,esbot= !esbot;break());
                    );
                );
            );
            // Final spawn based on tag values; esbot, nssbot, gsbot, for entity, namespace and global respectively
            nodata = (gsbot==null&&nssbot==null&&esbot==null);
            // Order of importance;
            shall_spawn = nodata||(gsbot&&nssbot!=false&&esbot!=false)||(nssbot&&esbot!=false)||esbot;
            if(!shall_spawn,
                modify(e, 'remove');
            );
            return();
        )
    );
)
