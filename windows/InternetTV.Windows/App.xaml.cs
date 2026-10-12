using System.IO;
using System.Windows;

namespace InternetTV.Windows;

public partial class App : Application
{
    public static string DataDir { get; } =
        Path.Combine(Environment.GetFolderPath(Environment.SpecialFolder.LocalApplicationData), "InternetTV");

    protected override void OnStartup(StartupEventArgs e)
    {
        Directory.CreateDirectory(DataDir);
        // Point LibVLC at the libvlc\win-x64 folder next to the exe explicitly:
        // in a single-file app, assembly locations are empty, so auto-discovery fails.
        LibVLCSharp.Shared.Core.Initialize(Path.Combine(AppContext.BaseDirectory, "libvlc", "win-x64"));

        DispatcherUnhandledException += (_, args) =>
        {
            File.AppendAllText(Path.Combine(DataDir, "error.log"), $"{DateTime.Now:u} {args.Exception}\n\n");
            MessageBox.Show(args.Exception.Message, "InternetTV error", MessageBoxButton.OK, MessageBoxImage.Error);
            args.Handled = true; // keep the app alive; errors are logged
        };
        base.OnStartup(e);
    }
}
