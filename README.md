# SSHClient 项目说明文档

## 简介
SSHClient 是一个使用 Java 编写的 SSH 客户端程序，它通过中转主机（Jump Host）实现端口转发，从而访问目标设备的内网端口。该程序使用了 `JSch` 库来建立 SSH 连接和设置端口转发。

## 功能
- 通过中转主机建立 SSH 连接。
- 设置端口转发，将本地端口转发到目标设备的内网端口。
- 支持用户输入中转主机地址、SSH 用户名、密码、目标设备 IP、目标端口和本地端口。

## 使用方法
1. 确保已安装 Java 开发环境（JDK）。
2. 下载并添加 `JSch` 库到项目中。
3. 编译并运行 `SSHClient.java`。

## 输入说明
程序运行后，会提示用户输入以下信息：
- 中转主机地址
- SSH 用户名
- SSH 密码
- 目标设备 IP
- 目标端口（5900 表示 VNC，22 表示 SSH）
- 本地端口（用于转发）

## 示例
```
Enter jump host address: jump.example.com
Enter SSH username: user
Enter SSH password: pass
Enter target device IP: 192.168.1.100
Do you want VNC or SSH? (5900/22): 5900
Enter local port for forwarding: 12345
```

## 注意事项
- 请确保输入的 IP 地址和端口正确无误。
- 在生产环境中，建议使用密钥认证代替密码认证，以提高安全性。
- 程序运行后会保持运行状态以维持端口转发，如需停止程序，请手动中断。

## 贡献
如果您有任何建议或改进意见，请随时提交 Pull Request 或创建 Issue。

## 许可证
该项目遵循 MIT 许可证。请查看 LICENSE 文件了解更多信息。

---

请注意，本文档中删除了所有个人可识别信息（PII）和网站超链接，以确保信息安全。