Option Strict On

Public Class Form1
    Inherits System.Windows.Forms.Form

#Region " Windows Form Designer generated code "

    Public Sub New()
        MyBase.New()

        'This call is required by the Windows Form Designer.
        InitializeComponent()

        'Add any initialization after the InitializeComponent() call

    End Sub

    'Form overrides dispose to clean up the component list.
    Protected Overloads Overrides Sub Dispose(ByVal disposing As Boolean)
        If disposing Then
            If Not (components Is Nothing) Then
                components.Dispose()
            End If
        End If
        MyBase.Dispose(disposing)
    End Sub

    'Required by the Windows Form Designer
    Private components As System.ComponentModel.IContainer

    'NOTE: The following procedure is required by the Windows Form Designer
    'It can be modified using the Windows Form Designer.  
    'Do not modify it using the code editor.
    Friend WithEvents label4 As System.Windows.Forms.Label
    Friend WithEvents label3 As System.Windows.Forms.Label
    Friend WithEvents label2 As System.Windows.Forms.Label
    Friend WithEvents lblLicense As System.Windows.Forms.Label
    Friend WithEvents txtLicenseKey As System.Windows.Forms.TextBox
    Friend WithEvents lblSpellResults As System.Windows.Forms.Label
    Friend WithEvents txtSpell As System.Windows.Forms.TextBox
    Friend WithEvents lblCacheResults As System.Windows.Forms.Label
    Friend WithEvents txtCachePage As System.Windows.Forms.TextBox
    Friend WithEvents lblSearchResults As System.Windows.Forms.Label
    Friend WithEvents txtSearchTerm As System.Windows.Forms.TextBox
    Friend WithEvents btnSearch As System.Windows.Forms.Button
    Friend WithEvents btnCache As System.Windows.Forms.Button
    Friend WithEvents btnSpell As System.Windows.Forms.Button
    <System.Diagnostics.DebuggerStepThrough()> Private Sub InitializeComponent()
        Me.lblSpellResults = New System.Windows.Forms.Label()
        Me.label4 = New System.Windows.Forms.Label()
        Me.txtSpell = New System.Windows.Forms.TextBox()
        Me.lblCacheResults = New System.Windows.Forms.Label()
        Me.label3 = New System.Windows.Forms.Label()
        Me.txtCachePage = New System.Windows.Forms.TextBox()
        Me.lblSearchResults = New System.Windows.Forms.Label()
        Me.label2 = New System.Windows.Forms.Label()
        Me.txtSearchTerm = New System.Windows.Forms.TextBox()
        Me.lblLicense = New System.Windows.Forms.Label()
        Me.txtLicenseKey = New System.Windows.Forms.TextBox()
        Me.btnSearch = New System.Windows.Forms.Button()
        Me.btnCache = New System.Windows.Forms.Button()
        Me.btnSpell = New System.Windows.Forms.Button()
        Me.SuspendLayout()
        '
        'lblSpellResults
        '
        Me.lblSpellResults.BackColor = System.Drawing.SystemColors.Window
        Me.lblSpellResults.ForeColor = System.Drawing.SystemColors.WindowText
        Me.lblSpellResults.Location = New System.Drawing.Point(120, 256)
        Me.lblSpellResults.Name = "lblSpellResults"
        Me.lblSpellResults.Size = New System.Drawing.Size(240, 16)
        Me.lblSpellResults.TabIndex = 28
        '
        'label4
        '
        Me.label4.Location = New System.Drawing.Point(8, 256)
        Me.label4.Name = "label4"
        Me.label4.Size = New System.Drawing.Size(112, 16)
        Me.label4.TabIndex = 27
        Me.label4.Text = "Spelling suggestion:"
        Me.label4.TextAlign = System.Drawing.ContentAlignment.TopRight
        '
        'txtSpell
        '
        Me.txtSpell.Location = New System.Drawing.Point(8, 224)
        Me.txtSpell.Name = "txtSpell"
        Me.txtSpell.Size = New System.Drawing.Size(280, 20)
        Me.txtSpell.TabIndex = 25
        Me.txtSpell.Text = "seperate pece"
        '
        'lblCacheResults
        '
        Me.lblCacheResults.BackColor = System.Drawing.SystemColors.Window
        Me.lblCacheResults.ForeColor = System.Drawing.SystemColors.WindowText
        Me.lblCacheResults.Location = New System.Drawing.Point(120, 176)
        Me.lblCacheResults.Name = "lblCacheResults"
        Me.lblCacheResults.Size = New System.Drawing.Size(240, 16)
        Me.lblCacheResults.TabIndex = 24
        '
        'label3
        '
        Me.label3.Location = New System.Drawing.Point(8, 176)
        Me.label3.Name = "label3"
        Me.label3.Size = New System.Drawing.Size(112, 16)
        Me.label3.TabIndex = 23
        Me.label3.Text = "Size of cached page:"
        Me.label3.TextAlign = System.Drawing.ContentAlignment.TopRight
        '
        'txtCachePage
        '
        Me.txtCachePage.Location = New System.Drawing.Point(8, 144)
        Me.txtCachePage.Name = "txtCachePage"
        Me.txtCachePage.Size = New System.Drawing.Size(280, 20)
        Me.txtCachePage.TabIndex = 21
        Me.txtCachePage.Text = "http://www.google.com/"
        '
        'lblSearchResults
        '
        Me.lblSearchResults.BackColor = System.Drawing.SystemColors.Window
        Me.lblSearchResults.ForeColor = System.Drawing.SystemColors.WindowText
        Me.lblSearchResults.Location = New System.Drawing.Point(120, 96)
        Me.lblSearchResults.Name = "lblSearchResults"
        Me.lblSearchResults.Size = New System.Drawing.Size(240, 16)
        Me.lblSearchResults.TabIndex = 20
        '
        'label2
        '
        Me.label2.Location = New System.Drawing.Point(8, 96)
        Me.label2.Name = "label2"
        Me.label2.Size = New System.Drawing.Size(112, 16)
        Me.label2.TabIndex = 19
        Me.label2.Text = "Est. # Results:"
        Me.label2.TextAlign = System.Drawing.ContentAlignment.TopRight
        '
        'txtSearchTerm
        '
        Me.txtSearchTerm.Location = New System.Drawing.Point(8, 64)
        Me.txtSearchTerm.Name = "txtSearchTerm"
        Me.txtSearchTerm.Size = New System.Drawing.Size(280, 20)
        Me.txtSearchTerm.TabIndex = 18
        Me.txtSearchTerm.Text = "Enter search term"
        '
        'lblLicense
        '
        Me.lblLicense.Location = New System.Drawing.Point(8, 16)
        Me.lblLicense.Name = "lblLicense"
        Me.lblLicense.Size = New System.Drawing.Size(104, 16)
        Me.lblLicense.TabIndex = 15
        Me.lblLicense.Text = "Enter license key:"
        '
        'txtLicenseKey
        '
        Me.txtLicenseKey.Location = New System.Drawing.Point(120, 16)
        Me.txtLicenseKey.Name = "txtLicenseKey"
        Me.txtLicenseKey.Size = New System.Drawing.Size(240, 20)
        Me.txtLicenseKey.TabIndex = 29
        Me.txtLicenseKey.Text = "xxxxxxxxxxxxxxxxxxxxxxxx"
        '
        'btnSearch
        '
        Me.btnSearch.Location = New System.Drawing.Point(296, 64)
        Me.btnSearch.Name = "btnSearch"
        Me.btnSearch.Size = New System.Drawing.Size(64, 24)
        Me.btnSearch.TabIndex = 30
        Me.btnSearch.Text = "Search"
        '
        'btnCache
        '
        Me.btnCache.Location = New System.Drawing.Point(296, 144)
        Me.btnCache.Name = "btnCache"
        Me.btnCache.Size = New System.Drawing.Size(64, 24)
        Me.btnCache.TabIndex = 31
        Me.btnCache.Text = "Cache"
        '
        'btnSpell
        '
        Me.btnSpell.Location = New System.Drawing.Point(296, 224)
        Me.btnSpell.Name = "btnSpell"
        Me.btnSpell.Size = New System.Drawing.Size(64, 24)
        Me.btnSpell.TabIndex = 32
        Me.btnSpell.Text = "Spell"
        '
        'Form1
        '
        Me.AutoScaleBaseSize = New System.Drawing.Size(5, 13)
        Me.ClientSize = New System.Drawing.Size(376, 293)
        Me.Controls.AddRange(New System.Windows.Forms.Control() {Me.btnSpell, Me.btnCache, Me.btnSearch, Me.txtLicenseKey, Me.lblSpellResults, Me.label4, Me.txtSpell, Me.lblCacheResults, Me.label3, Me.txtCachePage, Me.lblSearchResults, Me.label2, Me.txtSearchTerm, Me.lblLicense})
        Me.Name = "Form1"
        Me.Text = "Google Web APIs Demo (VB)"
        Me.ResumeLayout(False)

    End Sub

#End Region

    ' Search button: do a search, display number of results 
    Private Sub btnSearch_Click(ByVal sender As System.Object, ByVal e As System.EventArgs) Handles btnSearch.Click
        ' Create a Google Search object
        Dim s As New Google.GoogleSearchService()
        Try
            ' Invoke the search method
            Dim r As Google.GoogleSearchResult = s.doGoogleSearch(txtLicenseKey.Text, txtSearchTerm.Text, 0, 1, False, "", False, "", "", "")
            ' Extract the estimated number of results for the search and display it
            Dim estResults As Integer = r.estimatedTotalResultsCount
            lblSearchResults.Text = CStr(estResults)
        Catch ex As System.Web.Services.Protocols.SoapException
            MsgBox(ex.Message)
        End Try
    End Sub

    ' Cache button: look up a URL in the Google cache, display size of page
    Private Sub btnCache_Click(ByVal sender As System.Object, ByVal e As System.EventArgs) Handles btnCache.Click
        ' Create a Google Search object
        Dim s As New Google.GoogleSearchService()
        Try
            ' Invoke the doGetCachedPage method and get the cached bytes
            Dim bytes() As System.Byte = s.doGetCachedPage(txtLicenseKey.Text, txtCachePage.Text)
            ' Display the length of the cached page
            lblCacheResults.Text = CStr(bytes.Length)
        Catch ex As System.Web.Services.Protocols.SoapException
            MsgBox(ex.Message)
        End Try
    End Sub

    ' Spell button: ask Google for a suggested alternate spelling, display it
    Private Sub btnSpell_Click(ByVal sender As System.Object, ByVal e As System.EventArgs) Handles btnSpell.Click
        ' Create a Google Search object
        Dim s As New Google.GoogleSearchService()
        Try
            ' Ask for spelling suggestion
            Dim suggestion As String = s.doSpellingSuggestion(txtLicenseKey.Text, txtSpell.Text)
            ' Display the suggestion, if any
            If suggestion Is Nothing Then
                lblSpellResults.Text = "<no suggestion>"
            Else
                lblSpellResults.Text = suggestion
            End If
        Catch ex As System.Web.Services.Protocols.SoapException
            MsgBox(ex.Message)
        End Try
    End Sub
End Class
