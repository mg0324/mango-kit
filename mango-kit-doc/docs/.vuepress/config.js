module.exports = {
    base: '/mango-kit/',
    title: 'mango-kit文档',
    description: '',
    themeConfig: {
        nav: [
            { text: '首页', link: '/' },
            { text: '指南', link: '/zh/guide/' },
            { text: 'External', link: 'https://google.com' },
        ],
        sidebar: {
            '/zh/guide/':[
                '',
                'time',
                'excel',
                'validator'
            ]
        },
        displayAllHeaders: false
    }
}