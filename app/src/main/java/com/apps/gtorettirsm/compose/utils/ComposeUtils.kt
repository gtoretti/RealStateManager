/*
 */

package com.apps.gtorettirsm.compose.utils

import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.icu.text.MessageFormat
import android.os.Environment
import android.widget.LinearLayout
import android.widget.ListPopupWindow
import android.widget.ScrollView
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.FileProvider
//import com.google.android.gms.ads.AdRequest
//import com.google.android.gms.ads.AdView

import com.apps.gtorettirsm.data.Receiving
import com.apps.gtorettirsm.data.Property
import com.apps.gtorettirsm.data.Profile
import com.apps.gtorettirsm.data.Provider
import com.apps.gtorettirsm.data.ReceiptPDF
import com.apps.gtorettirsm.viewmodels.ReceivingViewModel
import java.io.File
import java.io.FileOutputStream
import java.math.RoundingMode
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import kotlin.time.Duration.Companion.milliseconds


fun showToast(text: String, context: Context) {
    val duration = Toast.LENGTH_LONG
    val toast = Toast.makeText(context, text, duration) // in Activity
    toast.show()
}

fun generatePDF(header: String,
                body: String,
                signingName: String,
                signingCPF: String,
                footer: String,
                context: Context,
                pdfFileName: String) {

    var pdfDocument: PdfDocument = PdfDocument()
    var title: Paint = Paint()
    var headerStyle: Paint = Paint()

    /**Dimension For A4 Size Paper**/
    var pageHeight = 842
    var pageWidth = 595

    var myPageInfo: PdfDocument.PageInfo? =
        PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 1).create()
    var myPage: PdfDocument.Page = pdfDocument.startPage(myPageInfo)
    var canvas: Canvas = myPage.canvas

    headerStyle.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL))
    headerStyle.textSize = 12F
    headerStyle.setColor(Color.BLACK)
    canvas.drawText(header, 50F, 50F, headerStyle)

    title.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL))
    title.textSize = 15F
    title.setColor(Color.BLACK)
    canvas.drawText("Recibo de Pagamento", 235F, 100F, title)


    var words = ArrayList(body.split(" "))
    var y=150F
    while (words.isNotEmpty()){
        var body1stLine = ""
        while (words.isNotEmpty() && body1stLine.length < 85){
            body1stLine = body1stLine + words.removeAt(0) + " "
            canvas.drawText(body1stLine, 50F, y, headerStyle)
        }
        y += 20
    }

    canvas.drawText(signingName, 300F, 400F, headerStyle)
    canvas.drawText(signingCPF, 300F, 420F, headerStyle)

    canvas.drawText(footer, 50F, 800F, headerStyle)

    pdfDocument.finishPage(myPage)

    val contextWrapper = ContextWrapper(context)
    val documentDirectory = contextWrapper.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
    val file = File(documentDirectory, pdfFileName + ".pdf")

    try {
        pdfDocument.writeTo(FileOutputStream(file))
        val fileURI = FileProvider.getUriForFile(
            context,
            context.applicationContext.packageName + ".provider",
            file
        )

        val browserIntent = Intent(Intent.ACTION_VIEW, fileURI)
        browserIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        context.startActivity(browserIntent)

    } catch (e: Exception) {
        Toast.makeText(context, "Erro ao gerar arquivo PDF.", Toast.LENGTH_SHORT).show()
    }
    pdfDocument.close()
}


fun filterMonthlyBillingsByMonth(list: List<Receiving>, month: Int, year: Int): List<Receiving> {
    var ret: ArrayList<Receiving> = ArrayList()
    list.forEach { a ->
        var d = Calendar.getInstance()
        //d.time = a.billingDate
        if (d.get(Calendar.MONTH) == month && d.get(Calendar.YEAR) == year) ret.add(a)
    }
    return ret
}

fun defaultNaoInformado(s: String): String {
    var r = "Não Informado."
    if (!s.trim().isEmpty())
        r = s
    return r
}

fun daysBetween(startDate: Calendar, endDate: Calendar): Long {
    val startMillis = startDate.timeInMillis
    val endMillis = endDate.timeInMillis
    val diffMillis = endMillis - startMillis
    return diffMillis / (24 * 60 * 60 * 1000)
}

fun daysBetween(startDate: Date, endDate: Date): Long {
    val startMillis = startDate.time
    val endMillis = endDate.time
    var diffMillis = endMillis - startMillis
    diffMillis /= (24 * 60 * 60 * 1000)
    return diffMillis
}

fun defaultNaoInformado(d: Date): String {
    var r = "Não Informado."
    var fmt = SimpleDateFormat("dd/MM/yyyy")
    if (!d.time.toString().equals("0"))
        r = fmt.format(d)
    return r
}

fun defaultNaoInformado(l: Int): String {
    var r = "Não Informado."
    if (!l.equals(0))
        r = l.toString()
    return r
}

fun defaultNaoInformado(l: Double): String {
    var r = "Não Informado."
    if (!l.toScreen().equals("0,00"))
        r = l.toScreen()
    return r
}

fun String.replaceLast(oldValue: String, newValue: String): String {
    val lastIndex = lastIndexOf(oldValue)
    if (lastIndex == -1) {
        return this
    }
    val prefix = substring(0, lastIndex)
    val suffix = substring(lastIndex + oldValue.length)
    return "$prefix$newValue$suffix"
}

fun Double.toWords(language: String, country: String): String {
    val formatter = MessageFormat(
        "{0,spellout,currency}", Locale(language, country)
    )
    return formatter.format(arrayOf(this))
}

fun String.screenToDouble(): Double {
    var ret = this.replace(".", "")
    ret = ret.replace(",", ".")
    return ret.toDouble()
}

fun Double.toScreen(): String {
    return this.toBigDecimal().setScale(2, RoundingMode.UP).toString().replace(".", ",")
}

fun generateReceipt(
    profile: Profile,
    patient: Property,
    list: List<Receiving>,
    selected: List<Long>,
    receiptDate: Calendar,
    context: Context,
    receivingViewModel: ReceivingViewModel,
) {

    var receiptTotalValue: Double = 0.0
    var sessionValue: Double = 0.0

    val sdfDay = SimpleDateFormat("dd")



    var receiptDateDescr =
       sdfDay.format(receiptDate.time) + " de " + " de " + receiptDate.get(Calendar.YEAR)


    var daysDescr = ""
    var qtdMonthlyBillings = 0

    var month:Int = 0
    var year:Int = 0
    list.forEach { monthlyBilling ->


    }

    if (daysDescr.length > 0) {
        daysDescr = daysDescr.substring(
            0, daysDescr.length - 3
        ) + " de " + " de " + year
        daysDescr = daysDescr.replaceLast(",", "e")
    }

    var sessao = "sessão"
    var dia = ""
    if (qtdMonthlyBillings > 1) {
        sessao = "sessões"
        dia = "s"
    }

    var header = profile.city + ", " + receiptDateDescr
    var body = "Declaro, para os devidos fins, que recebi de  a quantia de R$ " + receiptTotalValue.toBigDecimal()
    .setScale(2, RoundingMode.UP).toString()
        .replace(".", ",") + " (" + receiptTotalValue.toWords(
        "pt",
        "BR"
    ) + " reais) referente a " + qtdMonthlyBillings + " " + sessao + " de  para o(a) paciente " + patient.streetAddress +", no valor de R$ " + sessionValue.toBigDecimal()
        .setScale(2, RoundingMode.UP).toString().replace(
            ".",
            ","
        ) + " por sessão, realizada" + dia + " no" + dia + " dia" + dia + " " + daysDescr + "."

    var signingName = profile.name
    var signingCPF =  "CPF:" + profile.cpfCnpj
    var footer = profile.address + ". Telefone: " + profile.phoneNumber
    var pdfFileName = patient.streetAddress.replace(" ","_") + "_" + (receiptDate.get(Calendar.MONTH)+1) + "_" + receiptDate.get(Calendar.YEAR) + "-" + Date().time.milliseconds

    //setting received date same as receiptDate just because received date is not null
     var receiptPDF = ReceiptPDF(0,0,header,body,signingName,signingCPF,footer,pdfFileName)


    showToast("Recibo registrado com sucesso! Criando arquivo PDF para assinatura...", context)
    generatePDF(header,body,signingName, signingCPF, footer, context, pdfFileName)
}


@Composable
fun DrawScrollableView(content: @Composable () -> Unit, modifier: Modifier) {
    AndroidView(
        modifier = modifier,
        factory = {
            val scrollView = ScrollView(it)
            val layout = LinearLayout.LayoutParams(
                ListPopupWindow.MATCH_PARENT,
                ListPopupWindow.MATCH_PARENT
            )
            scrollView.layoutParams = layout
            scrollView.isVerticalFadingEdgeEnabled = true
            scrollView.isScrollbarFadingEnabled = false
            scrollView.addView(ComposeView(it).apply {
                setContent {
                    content()
                }
            })
            val linearLayout = LinearLayout(it)
            linearLayout.orientation = LinearLayout.VERTICAL
            linearLayout.layoutParams = LinearLayout.LayoutParams(
                ListPopupWindow.MATCH_PARENT,
                ListPopupWindow.WRAP_CONTENT
            )
            linearLayout.addView(scrollView)
            linearLayout
        }
    )
}


@Composable
fun AndroidViewAdView() {
    val context = LocalContext.current
    /*
    AndroidView(
        factory = {
            AdView(context).apply {
                setAdSize(com.google.android.gms.ads.AdSize.BANNER)
                adUnitId = "ca-app-pub-7831452611438581/4248551674"
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                loadAd(AdRequest.Builder().build())
            }
        }
    )
    */
}

@Composable
fun isDarkTheme(): Boolean {
    val context = LocalContext.current
    return context.resources.configuration.uiMode and
            Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
}

@Composable
fun getTextColor(): androidx.compose.ui.graphics.Color {
    if (isDarkTheme())
        return androidx.compose.ui.graphics.Color(0xFFC0D1DF)
    else
        return androidx.compose.ui.graphics.Color(0xFF002D52)
}

@Composable
fun getRedTextColor(): androidx.compose.ui.graphics.Color {
    if (isDarkTheme())
        return androidx.compose.ui.graphics.Color(0xFFF06292)
    else
        return androidx.compose.ui.graphics.Color(0xFFD50000)
}

@Composable
fun getButtonColor(): androidx.compose.ui.graphics.Color {
    if (isDarkTheme())
        return androidx.compose.ui.graphics.Color(0xFF86B4DA)
    else
        return androidx.compose.ui.graphics.Color(0xFF4479A3)
}


fun getProviderServicesList(p: Provider): ArrayList<String> {
    var ret = ArrayList<String>()

    if (p.servicesAdministration==1)
        ret.add("Administração de Reformas")
    if (p.servicesBrickwork==1)
        ret.add("Alvenaria")
    if (p.servicesArchitecture==1)
        ret.add("Arquitetura")
    if (p.servicesInsurer==1)
        ret.add("Asseguradora")
    if (p.servicesAutomation==1)
        ret.add("Automação Residencial")
    if (p.servicesFireBrigade==1)
        ret.add("Brigada de Incêndio")
    if (p.servicesNotary==1)
        ret.add("Cartório de Imóveis")
    if (p.servicesAluminumFrames==1)
        ret.add("Esquadrias de Alumínio")
    if (p.servicesPlasterer==1)
        ret.add("Gesseiro")
    if (p.servicesElectric==1)
        ret.add("Instalações Elétricas")
    if (p.servicesHydraulic==1)
        ret.add("Instalações Hidráulicas")
    if (p.servicesCleaning==1)
        ret.add("Limpeza Pós-Obra")
    if (p.servicesOthers==1)
        ret.add("Outros")
    if (p.servicesMaterialSupplier==1)
        ret.add("Fornecedor de Materiais")
    if (p.servicesPoolCleaning==1)
        ret.add("Limpeza de Piscina")
    if (p.servicesAirConditioningMaintenance==1)
        ret.add("Manutenção de Ar-Condicionado")
    if (p.servicesCabinetsJoinery==1)
        ret.add("Manutenção de Armários e Marcenaria")
    if (p.servicesShowerStalls==1)
        ret.add("Manutenção de Boxes para Banheiros")
    if (p.servicesRoofer==1)
        ret.add("Manutenção de Calhas e Telhado")
    if (p.servicesElectricFence==1)
        ret.add("Manutenção de Cerca Elétrica")
    if (p.servicesSunshades==1)
        ret.add("Manutenção de Coberturas e Toldos")
    if (p.servicesCurtains==1)
        ret.add("Manutenção de Cortinas")
    if (p.servicesElevatorMaintenance==1)
        ret.add("Manutenção de Elevador")
    if (p.servicesElectronicIntercom==1)
        ret.add("Manutenção de Interfones")
    if (p.servicesGardening==1)
        ret.add("Manutenção de Jardim")
    if (p.servicesPoolMaintenance==1)
        ret.add("Manutenção de Piscina")
    if (p.servicesPlaygroundMaintenance==1)
        ret.add("Manutenção de Playground")
    if (p.servicesElectronicGate==1)
        ret.add("Manutenção de Portão Eletrônico")
    if (p.servicesLandscaping==1)
        ret.add("Paisagismo")
    if (p.servicesPainting==1)
        ret.add("Pintura")
    if (p.servicesSteelGatesRailings==1)
        ret.add("Portões e Grades de Aço")
    if (p.servicesPropertySecurity==1)
        ret.add("Segurança Patrimonial")

    return ret
}

fun getProviderDesc(p: Provider): String{
    var r = "("
    if (p.servicesAdministration==1)
        r += "Administração de Reformas, "
    if (p.servicesBrickwork==1)
        r += "Alvenaria, "
    if (p.servicesArchitecture==1)
        r += "Arquitetura, "
    if (p.servicesInsurer==1)
        r += "Asseguradora, "
    if (p.servicesAutomation==1)
        r += "Automação Residencial, "
    if (p.servicesFireBrigade==1)
        r += "Brigada de Incêndio, "
    if (p.servicesNotary==1)
        r += "Cartório de Imóveis, "
    if (p.servicesAluminumFrames==1)
        r += "Esquadrias de Alumínio, "
    if (p.servicesPlasterer==1)
        r += "Gesseiro, "
    if (p.servicesElectric==1)
        r += "Instalações Elétricas, "
    if (p.servicesHydraulic==1)
        r += "Instalações Hidráulicas, "
    if (p.servicesCleaning==1)
        r += "Limpeza Pós-Obra, "
    if (p.servicesOthers==1)
        r += "Outros, "
    if (p.servicesMaterialSupplier==1)
        r += "Fornecedor de Materiais, "
    if (p.servicesPoolCleaning==1)
        r += "Limpeza de Piscina, "
    if (p.servicesAirConditioningMaintenance==1)
        r += "Manutenção de Ar-Condicionado, "
    if (p.servicesCabinetsJoinery==1)
        r += "Manutenção de Armários e Marcenaria, "
    if (p.servicesShowerStalls==1)
        r += "Manutenção de Boxes para Banheiros, "
    if (p.servicesRoofer==1)
        r += "Manutenção de Calhas e Telhado, "
    if (p.servicesElectricFence==1)
        r += "Manutenção de Cerca Elétrica, "
    if (p.servicesSunshades==1)
        r += "Manutenção de Coberturas e Toldos, "
    if (p.servicesCurtains==1)
        r += "Manutenção de Cortinas, "
    if (p.servicesElevatorMaintenance==1)
        r += "Manutenção de Elevador, "
    if (p.servicesElectronicIntercom==1)
        r += "Manutenção de Interfones, "
    if (p.servicesGardening==1)
        r += "Manutenção de Jardim, "
    if (p.servicesPoolMaintenance==1)
        r += "Manutenção de Piscina, "
    if (p.servicesPlaygroundMaintenance==1)
        r += "Manutenção de Playground, "
    if (p.servicesElectronicGate==1)
        r += "Manutenção de Portão Eletrônico, "
    if (p.servicesLandscaping==1)
        r += "Paisagismo, "
    if (p.servicesPainting==1)
        r += "Pintura, "
    if (p.servicesSteelGatesRailings==1)
        r += "Portões e Grades de Aço, "
    if (p.servicesPropertySecurity==1)
        r += "Segurança Patrimonial, "
    r += ")"

    r = r.replace(", )",")")

    return p.name + ":" + r
}