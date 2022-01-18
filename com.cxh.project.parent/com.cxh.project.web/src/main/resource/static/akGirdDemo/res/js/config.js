require([ '../ak/scripts/common'], function() {
	require.config({
        paths : {
            'akGirdDemo' : "/akGirdDemo/controller/akGirdDemo",
            'common': "/common/js/akCommon"
        }
    });
    require(['akGirdDemo'], function(my){
    	my.render();
    });
});
