require(['../ak/scripts/common'], function (common) {
	require.config({
		paths: {
			'akViewDemo':"/akViewDemo/controller/akViewDemo",
			'common': "/common/js/akCommon"
		}
	});
	require(['akViewDemo'], function (my) {
		my.render();
	});
});


