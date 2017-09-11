package org.tiestvilee.kaychtml

import org.tiestvilee.kaychtml.impl.*

fun attr(name: String) = KAttribute(name, "")
infix fun String.attr(value: String) = KAttribute(this, value)

fun id(id: String) = Id(id)
fun cl(className: String) = Class(className)

fun doctype(vararg params: KElement): Doctype = Doctype(*params)

fun `a`(vararg params: KElement): A = A(*params)

fun `abbr`(vararg params: KElement): Abbr = Abbr(*params)

fun `address`(vararg params: KElement): Address = Address(*params)

fun `area`(vararg params: KElement): Area = Area(*params)

fun `article`(vararg params: KElement): Article = Article(*params)

fun `aside`(vararg params: KElement): Aside = Aside(*params)

fun `audio`(vararg params: KElement): Audio = Audio(*params)

fun `b`(vararg params: KElement): B = B(*params)

fun `base`(vararg params: KElement): Base = Base(*params)

fun `bdi`(vararg params: KElement): Bdi = Bdi(*params)

fun `bdo`(vararg params: KElement): Bdo = Bdo(*params)

fun `bgsound`(vararg params: KElement): Bgsound = Bgsound(*params)

fun `blockquote`(vararg params: KElement): Blockquote = Blockquote(*params)

fun `body`(vararg params: KElement): Body = Body(*params)

fun `br`(vararg params: KElement): Br = Br(*params)

fun `button`(vararg params: KElement): Button = Button(*params)

fun `canvas`(vararg params: KElement): Canvas = Canvas(*params)

fun `caption`(vararg params: KElement): Caption = Caption(*params)

fun `cite`(vararg params: KElement): Cite = Cite(*params)

fun `code`(vararg params: KElement): Code = Code(*params)

fun `col`(vararg params: KElement): Col = Col(*params)

fun `colgroup`(vararg params: KElement): Colgroup = Colgroup(*params)

fun `data`(vararg params: KElement): Data = Data(*params)

fun `datalist`(vararg params: KElement): Datalist = Datalist(*params)

fun `dd`(vararg params: KElement): Dd = Dd(*params)

fun `del`(vararg params: KElement): Del = Del(*params)

fun `details`(vararg params: KElement): Details = Details(*params)

fun `dfn`(vararg params: KElement): Dfn = Dfn(*params)

fun `dialog`(vararg params: KElement): Dialog = Dialog(*params)

fun `div`(vararg params: KElement): Div = Div(*params)

fun `dl`(vararg params: KElement): Dl = Dl(*params)

fun `dt`(vararg params: KElement): Dt = Dt(*params)

fun `em`(vararg params: KElement): Em = Em(*params)

fun `embed`(vararg params: KElement): Embed = Embed(*params)

fun `fieldset`(vararg params: KElement): Fieldset = Fieldset(*params)

fun `figcaption`(vararg params: KElement): Figcaption = Figcaption(*params)

fun `figure`(vararg params: KElement): Figure = Figure(*params)

fun `footer`(vararg params: KElement): Footer = Footer(*params)

fun `form`(vararg params: KElement): Form = Form(*params)

fun `h1`(vararg params: KElement): H1 = H1(*params)

fun `h2`(vararg params: KElement): H2 = H2(*params)

fun `h3`(vararg params: KElement): H3 = H3(*params)

fun `h4`(vararg params: KElement): H4 = H4(*params)

fun `h5`(vararg params: KElement): H5 = H5(*params)

fun `h6`(vararg params: KElement): H6 = H6(*params)

fun `head`(vararg params: KElement): Head = Head(*params)

fun `header`(vararg params: KElement): Header = Header(*params)

fun `hgroup`(vararg params: KElement): Hgroup = Hgroup(*params)

fun `hr`(vararg params: KElement): Hr = Hr(*params)

fun `html`(vararg params: KElement): Html = Html(*params)

fun `i`(vararg params: KElement): I = I(*params)

fun `iframe`(vararg params: KElement): Iframe = Iframe(*params)

fun `img`(vararg params: KElement): Img = Img(*params)

fun `input`(vararg params: KElement): Input = Input(*params)

fun `ins`(vararg params: KElement): Ins = Ins(*params)

fun `kbd`(vararg params: KElement): Kbd = Kbd(*params)

fun `label`(vararg params: KElement): Label = Label(*params)

fun `legend`(vararg params: KElement): Legend = Legend(*params)

fun `li`(vararg params: KElement): Li = Li(*params)

fun `link`(vararg params: KElement): Link = Link(*params)

fun `main`(vararg params: KElement): Main = Main(*params)

fun `map_`(vararg params: KElement): Map_ = Map_(*params)

fun `mark`(vararg params: KElement): Mark = Mark(*params)

fun `menu`(vararg params: KElement): Menu = Menu(*params)

fun `menuitem`(vararg params: KElement): Menuitem = Menuitem(*params)

fun `meta`(vararg params: KElement): Meta = Meta(*params)

fun `meter`(vararg params: KElement): Meter = Meter(*params)

fun `nav`(vararg params: KElement): Nav = Nav(*params)

fun `nobr`(vararg params: KElement): Nobr = Nobr(*params)

fun `noframes`(vararg params: KElement): Noframes = Noframes(*params)

fun `noscript`(vararg params: KElement): Noscript = Noscript(*params)

fun `object`(vararg params: KElement): Object = Object(*params)

fun `ol`(vararg params: KElement): Ol = Ol(*params)

fun `optgroup`(vararg params: KElement): Optgroup = Optgroup(*params)

fun `option`(vararg params: KElement): Option = Option(*params)

fun `output`(vararg params: KElement): Output = Output(*params)

fun `p`(vararg params: KElement): P = P(*params)

fun `param`(vararg params: KElement): Param = Param(*params)

fun `picture`(vararg params: KElement): Picture = Picture(*params)

fun `pre`(vararg params: KElement): Pre = Pre(*params)

fun `progress`(vararg params: KElement): Progress = Progress(*params)

fun `q`(vararg params: KElement): Q = Q(*params)

fun `rp`(vararg params: KElement): Rp = Rp(*params)

fun `rt`(vararg params: KElement): Rt = Rt(*params)

fun `rtc`(vararg params: KElement): Rtc = Rtc(*params)

fun `ruby`(vararg params: KElement): Ruby = Ruby(*params)

fun `s`(vararg params: KElement): S = S(*params)

fun `samp`(vararg params: KElement): Samp = Samp(*params)

fun `script`(vararg params: KElement): Script = Script(*params)

fun `section`(vararg params: KElement): Section = Section(*params)

fun `select`(vararg params: KElement): Select = Select(*params)

fun `slot`(vararg params: KElement): Slot = Slot(*params)

fun `small`(vararg params: KElement): Small = Small(*params)

fun `source`(vararg params: KElement): Source = Source(*params)

fun `span`(vararg params: KElement): Span = Span(*params)

fun `strong`(vararg params: KElement): Strong = Strong(*params)

fun `style`(vararg params: KElement): Style = Style(*params)

fun `sub`(vararg params: KElement): Sub = Sub(*params)

fun `summary`(vararg params: KElement): Summary = Summary(*params)

fun `sup`(vararg params: KElement): Sup = Sup(*params)

fun `table`(vararg params: KElement): Table = Table(*params)

fun `tbody`(vararg params: KElement): Tbody = Tbody(*params)

fun `td`(vararg params: KElement): Td = Td(*params)

fun `template`(vararg params: KElement): Template = Template(*params)

fun `textarea`(vararg params: KElement): Textarea = Textarea(*params)

fun `tfoot`(vararg params: KElement): Tfoot = Tfoot(*params)

fun `th`(vararg params: KElement): Th = Th(*params)

fun `thead`(vararg params: KElement): Thead = Thead(*params)

fun `time`(vararg params: KElement): Time = Time(*params)

fun `title`(vararg params: KElement): Title = Title(*params)

fun `tr`(vararg params: KElement): Tr = Tr(*params)

fun `track`(vararg params: KElement): Track = Track(*params)

fun `u`(vararg params: KElement): U = U(*params)

fun `ul`(vararg params: KElement): Ul = Ul(*params)

fun `var`(vararg params: KElement): Var = Var(*params)

fun `video`(vararg params: KElement): Video = Video(*params)

fun `wbr`(vararg params: KElement): Wbr = Wbr(*params)

