module.exports = {
    base: '/mango-kit/',
    head: [
        ['link', { rel: 'icon', href: '/favicon.ico' }]
    ],
    title: 'mango-kit文档 | java工具集',
    description: 'java工具集',
    themeConfig: {
        logo: '/favicon.ico',
        nav: [
            { text: '首页', link: '/' },
            { text: '指南', link: '/zh/guide/' }
        ],
        sidebarDepth: 2,
        sidebar: {
            '/zh/guide/':[
                '',
                'time',
                'excel',
                'validator',
                'fast'
            ]
        },
        displayAllHeaders: false
    },
    markdown: {
        lineNumbers: true
    }
}