
function initFusionChart(chartId,chartType,chartDiv,chartUrl)
{
	var width = "250";
	var height="133";
	
    //CR29075:disable detectFlashVersion and autoInstallRedirect, otherwise, IE would display flash player download within page
	var chartRef = new FusionCharts("/appmng/pages/monitor/flash/"+chartType, chartId, width, height, "0","1",null,"noScale","EN","0","0");
	//chartUrl="https://10.228.22.9:9443/appmng/data.xml"
	//var chartRef = new FusionCharts("/"+chartType, chartId, width, height, "0","1",null,"noScale","EN","0","0");
	//CR25129:make chart to be transparent to avoid ihelp overlapped Qiuyun Zhong 2009-09-04
	chartRef.setTransparent(true);
	var encodedUrl = escape( chartUrl);
	chartRef.setDataURL(encodedUrl);
	chartRef.render(chartDiv);
}
function currentTime()
{
	var currDate = new Date();
	var currTime = currDate.getHours() + ":" + currDate.getMinutes() + ":" + currDate.getSeconds();
	return currTime;
}
function writeFusionChart( chartId, chartType )
{		var width = "250";
		var height="133";

		var noQual ="Retrieving Data...";
		var plsWait = "Please Wait...";
		document.write('<object classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000" codebase="https://fpdownload.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=8,0,0,0" width="');
		document.write(width);
		document.write('" height="'+ height+'" id="'+ chartId +'" >');
		document.write('<param name="movie" value="/appmng/pages/monitor/flash/'+ chartType +'?ChartNoDataText=' + noQual + '" />');
		document.write('<param name="FlashVars" value="&dataXML=<chart></chart>&registerWithJS=1&ChartNoDataText=' + plsWait + '" />');
		document.write('<param name="quality" value="high" />');
		document.write('<param name="wmode" value="transparent" />');
		document.write('<embed src="/appmng/pages/monitor/flash/'+chartType +'?ChartNoDataText=' + noQual + '" flashVars="&dataXML=data.xml&registerWithJS=1&ChartNoDataText=' + plsWait + '" quality="high" wmode="transparent" width="');
		document.write(width);
		document.write('" height="'+ height+'" name="'+ chartId+'" type="application/x-shockwave-flash" pluginspage="http://www.macromedia.com/go/getflashplayer" />');
		document.write('</object>');
}	

  /**
  **restart update for the chart be stopped previous, say restarting module
  **/
  function restartUpdate( chartId )
  {
  	//alert("restart update "+ chartId);
	var chart  = getChartFromId(chartId);
	chart.restartUpdate();

  }

	/**
	** clear chart data
	**/
   function clearChart( chartId)
   {
   	 var chartToClear = getChartFromId(chartId);
   	 chartToClear.clearChart();
   }
   
   /**
   ** stop update chart data
   **/
   function stopUpdate( chartId)
   {
   	var chartStopUpdate = getChartFromId(chartId);
   	chartStopUpdate.stopUpdate();
   }